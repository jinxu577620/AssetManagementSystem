<template>
    <div>
        <div class="avatar-uploader">
            <img v-if="watchUploadInfo.imgSrc" :src="watchUploadInfo.imgSrc" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            <input ref="videoFile" type="file" name="video" class="upload-input" @change="changFile"/>
        </div>
        <el-progress class="progressStyle" type="dashboard" :percentage="watchUploadInfo.percent" :color="colors"></el-progress>
        <!--<el-button @click="upload">默认按钮</el-button>-->
    </div>
</template>

<script>
    export default {
        name: "MyVueVideoUpload",
        props: {
            uploadPath:{
                type: String,
                default: 'test/'
            },
            currentImg:{
                type: String,
                default: ''
            }
        },
        data() {
            return {
                file: null,
                watchUploadInfo:{
                    imgSrc: this.currentImg,  // 用储存视频第一秒的截帧的图片
                    url: "",                  // 储存上传后的视频url
                    seconds: 0,               // 视频时长秒数
                    percent: 0      // 这边主要把percent放在对象中，然后传进全局变量函数中去更改数值
                                    // vue组件这边不能监听this.$oss中数值的变化，所以只能传过去变化，并且要对象才行
                },
                colors: [
                    {color: '#f56c6c', percentage: 20},
                    {color: '#e6a23c', percentage: 40},
                    {color: '#6f7ad3', percentage: 60},
                    {color: '#1989fa', percentage: 80},
                    {color: '#5cb87a', percentage: 100}
                ]
            };
        },
        methods: {
            async changFile(e){
                this.file = e.target.files[0];
                this.watchUploadInfo.imgSrc = require("../../assets/img/video.jpg");
                this.getVideoTimeSize(this.file);
            },
            // 获取视频时长
            getVideoTimeSize(file){
                let url = URL.createObjectURL(file);
                let audioElement = new Audio(url);
                const that = this;
                audioElement.addEventListener("loadedmetadata", function (_event) {
                    // console.log(audioElement.duration); //时长为秒，小数，182.36
                    that.watchUploadInfo.seconds = Math.round(audioElement.duration);
                });
            },
            async upload() {
                if (this.file != null) {
                    await this.$oss.upload(this.file, this.uploadPath, this.watchUploadInfo);
                }
                return this.watchUploadInfo;
            }

        }
    }
</script>

<style lang="scss" scoped>
    .avatar-uploader{
        display: inline-block;
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
        color: #97a8be;
        font-size: 14px;
        text-align: center;
        outline: none;
        background-color: #fff;
        width: 180px;
        height: 126px;
        .avatar-uploader-icon {
            font-size: 28px;
            color: #8c939d;
            text-align: center;
            width: 180px;
            height: 126px;
            line-height: 126px;
        }
        .avatar {
            width: 100%;
            height: 100%;
            display: inline-block;
        }
        .upload-input{
            width: 100%;
            height: 100%;
            z-index: 1;
            opacity: 0;
            position: absolute;
            left: 0;
            cursor: pointer;
        }
    }
    .avatar-uploader:hover {
        border-color: #409EFF;
    }
    .progressStyle{
        display: inline-block;
        margin-left: 25px;
        top: 5px;
    }

</style>
