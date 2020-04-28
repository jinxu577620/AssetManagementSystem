import apis from '../apis/index.js';
import { Message, Loading } from 'element-ui';
import SparkMD5 from 'spark-md5' // 依赖库，对文件进行md5加密唯一标识
import OSS from 'ali-oss';   // 阿里云OSS储存
import moment from 'moment'   // 日期处理类库

const oss = {
    ossClient: null,          // oss客户端实例
    expiration: '',           // 过期时间
    ossUrl: '',               // oss url路径地址前缀
    partSize: 2 * 1024 * 1024,    // 每个分片大小(byte)
    parallel: 5,              // 同时上传的分片数
    pointerCache: {           // 所有分片上传文件的检查点，储存在localStorage，中断下次可以断点续传
        get(file, name) {
            const cache = localStorage.getItem('uploadCacheKey')
            if (!cache) return null
            const cacheJson = JSON.parse(cache)
            if (cacheJson.name !== name) return null
            cacheJson.file = file
            return cacheJson
        },
        set(pointer) {
            localStorage.setItem('uploadCacheKey', JSON.stringify(pointer))
        },
        remove() {
            localStorage.removeItem('uploadCacheKey')
        }
    },

    // 获取 STS 凭证，创建 OSS Client
    async initOSSClient() {
        // 获取STS Token
        let res = await apis.getOssToken();
        if (res && res.code === 0){
            // 获取过期时间
            this.expiration = res.data.expiration;
            // 拼接url，后面有用到
            this.ossUrl = `https://${res.data.bucket}.${res.data.region}.aliyuncs.com/`
            this.ossClient = new OSS(res.data);
        }else {
            Message.error("请求OSS STS临时授权失败");
        }
    },

    // 获取文件内容MD5名称，放置重复上传
    getName(file) {
        return new Promise(resolve => {
            const {name} = file
            const suffix = name.substr(name.lastIndexOf('.'))
            const spark = new SparkMD5.ArrayBuffer()

            const reader = new FileReader()
            reader.readAsArrayBuffer(file)

            // loading 文件过大的时候读取时间非常长，加载显示
            let loading = Loading.service({
                lock: true,
                text: 'Loading',
                spinner: 'el-icon-loading',
                background: 'rgba(0, 0, 0, 0)'
            });

            reader.addEventListener('load', (e) => {
                spark.append(e.target.result);
                loading.close();
                resolve(spark.end() + suffix) // 文件md5加密，防止重复上传
            })
        })
    },

    // 上传操作， 第三个参数是由调用组件传进来，是为了监听进度情况，从而改变调用组件中相应的值，
    // 比如改变调用组件中进度条的值，这里必须是对象，否则调用组件中值不会改变，可为空
    async upload(file, path, watchUploadInfo) {
        // 如果文件大学小于分片大小，使用普通上传，否则使用分片上传
        if (file.size < this.partSize) {
            return await this.commonUpload(file, path, watchUploadInfo);
        } else {
            return await this.multipartUpload(file, path, watchUploadInfo);
        }
    },

    // 普通上传
    async commonUpload(file, path, watchUploadInfo) {
        if (!this.ossClient) {
            await this.initOSSClient();
        }
        if (this.expiration && moment(this.expiration).subtract(1, 'minute').isBefore(moment())) {
            console.log(`STS token 将在 1 分钟内过期，将暂停上传，重新获得STS token后继续上传`);
            if (this.ossClient) {
                this.ossClient.cancel();
            }
            await this.initOSSClient();
        }
        try {
            const fileName = await this.getName(file);

            // 向OSS上传头像
            let result = await this.ossClient.put(path+fileName, file);
            // 设置该文件的访问权限为公共读
            await this.ossClient.putACL(path+fileName, 'public-read');

            if (result.res.statusCode === 200) {
                if (watchUploadInfo)
                    watchUploadInfo.percent = 100;
                return result.url;
            } else {
                Message.error('上传失败')
            }
        } catch (e) {
            Message.error("上传错误");
        }
        return null;
    },

    // 分片上传，断点续传，返回进度条信息
    async multipartUpload(file, path, watchUploadInfo) {

        if (!this.ossClient) {
            await this.initOSSClient();
        }
        if (this.expiration && moment(this.expiration).subtract(1, 'minute').isBefore(moment())) {
            console.log(`STS token 将在 1 分钟内过期，将暂停上传，重新获得STS token后继续上传`);
            if (this.ossClient) {
                this.ossClient.cancel();
            }
            await this.initOSSClient();
        }

        try {
            const fileName = await this.getName(file);
            let checkpoint = this.pointerCache.get(file, path + fileName) // 断点续传指针

            // 失败后尝试两次
            for (let i = 0; i < 2; i++) {
                try {
                    const that = this;
                    const result = await this.ossClient.multipartUpload(path + fileName, file, {
                        parallel: that.parallel,
                        partSize: that.partSize,
                        checkpoint,
                        async progress(percentage, cpt) { // 上传进度
                            // 判断STS Token是否将要过期，过期则重新获取
                            that.ifExpiration(file, path, watchUploadInfo);
                            // console.log(parseInt(percentage * 100, 10))
                            if (watchUploadInfo)
                                watchUploadInfo.percent = parseInt(percentage * 100, 10);
                            that.pointerCache.set(cpt)
                            checkpoint = cpt
                        },
                    })
                    // 设置该文件的访问权限为公共读
                    await this.ossClient.putACL(path + fileName, 'public-read');
                    this.pointerCache.remove()
                    // console.log('上传成功：', result)
                    if (watchUploadInfo && this.isVideoFile(fileName)){
                        // 如果是视频文件，在watchUploadInfo中返回视频第1s的视频截帧的图片
                        watchUploadInfo.imgSrc= this.ossUrl+result.name+"?x-oss-process=video/snapshot,t_1000,f_jpg,w_715,h_500,m_fast";
                        watchUploadInfo.url = this.ossUrl + result.name;
                    }
                    return this.ossUrl + result.name;
                } catch (e) {
                    Message.error('上传失败：')
                }
            }

        } catch (e) {
            Message.error("上传错误");
        }
        return null;
    },

    // 判断STS Token是否将要过期，过期则重新获取
    async ifExpiration(file, path, watchUploadInfo){
        if (this.expiration && moment(this.expiration).subtract(1, 'minute').isBefore(moment())) {
            console.log(`STS token 将在 1 分钟内过期，将暂停上传，重新获得STS token后继续上传`);
            if (this.ossClient) {
                this.ossClient.cancel();
            }
            await this.initOSSClient();
            await this.multipartUpload(file, path, watchUploadInfo);
        }
    },

    // 判断是否是视频文件
    isVideoFile(fileName){
        let videoType=["avi","wmv","mkv","mp4","mov","rm","3gp","flv","mpg","rmvb"];
        if(RegExp("\\.(" + videoType.join("|") + ")$", "i").test(fileName.toLowerCase())) {
            return true;
        } else {
            return false;
        }
    },

    // 暂停上传
    stop() {
        if (this.ossClient) this.ossClient.cancel();
    },

    // 续传
    resume(file, path, watchUploadInfo) {
        if (this.ossClient){
            this.upload(file, path, watchUploadInfo);
        }
    },

    // 删除单个文件，参数是包不包含url都可以
    async delete(fileUrl){
        if (!this.ossClient) {
            await this.initOSSClient();
        }
        try {
            let result = await this.ossClient.delete(fileUrl.replace(this.ossUrl, ''));
            if (result.res.statusCode === 200) {
                console.log("删除成功")
            } else {
                Message.error('删除失败')
            }
        } catch (e) {
            Message.error("删除错误");
        }
    },

    // 批量删除objects
    async deleteMulti(fileObjects) {

        if (!this.ossClient) {
            await this.initOSSClient();
        }

        try {
            let result = await this.ossClient.deleteMulti(fileObjects, {
                quiet: true   //通过quiet参数来指定是否返回删除的结果
            });
            if (result.res.statusCode === 200) {
                console.log("删除成功")
            } else {
                Message.error('删除失败')
            }
        } catch (e) {
            Message.error("删除错误");
        }
    }
}


export default oss;
