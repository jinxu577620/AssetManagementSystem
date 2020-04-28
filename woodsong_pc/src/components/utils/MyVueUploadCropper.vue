<template>
    <div>
        <div class="avatar-uploader" :style="imgStyle">
            <img v-if="cropImg" :src="cropImg" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon" :style="imgStyle"></i>
            <input ref="imgFile" type="file" name="image" class="upload-input" accept="image/*" @change="setImage">
        </div>

        <el-dialog title="裁剪图片" :visible.sync="dialogVisible" width="30%">
            <vue-cropper
                    ref='cropper'
                    :src="imgSrc"
                    :aspectRatio="imgWidth/imgHeight"
                    :ready="cropImage"
                    :zoom="cropImage"
                    :cropmove="cropImage"
                    style="width:100%;height:300px;"
            ></vue-cropper>
            <span slot="footer" class="dialog-footer">
                    <el-button @click="cancelCrop">取 消</el-button>
                    <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
                </span>
        </el-dialog>
    </div>
</template>

<script>
    import VueCropper from 'vue-cropperjs';
    import 'cropperjs/dist/cropper.css';
    export default {
        name: "MyVueUploadCropper",
        components: { VueCropper},
        props: {
            currentImg: {
                type: String,
                default: ''
            },
            imgWidth: {
                type: Number,
                default: 160
            },
            imgHeight: {
                type: Number,
                default: 160
            },
            uploadPath:{
                type: String,
                default: ''
            }
        },
        data() {
            return {
                cropImg: this.currentImg,      // 裁剪后的图片，给img进行显示
                imgSrc: '',    // 选择的原图，在upload组件的on-change事件中给cropper
                dialogVisible: false,    // 显示裁剪弹窗

                fileInfo:{      // 储存打开的文件信息,之后从base64转为文件的时候会用到
                    name: "",
                    type: ""
                },
            };
        },
        computed: {
            imgStyle: function () {
                return {
                    'width': this.imgWidth + "px",
                    'height': this.imgHeight + "px",
                    'line-height': this.imgHeight + "px"
                }
            }
        },
        watch: {
            // 创建组件完成之后，需要监听传进来的currentImg的变化，从而改变之后的cropImg的初始化
            currentImg: function () {
                this.cropImg = this.currentImg;
            }
        },
        methods: {
            setImage(e){
                const file = e.target.files[0];
                if (!file.type.includes('image/')) {
                    return;
                }
                this.fileInfo.name = file.name; // 获取文件名
                this.fileInfo.type = file.type; // 获取类型

                // 看支持不支持FileReader
                if (!window.FileReader) return;
                const reader = new FileReader();
                reader.onload = (event) => {
                    this.$confirm('是否需要裁剪?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {
                        this.dialogVisible = true;
                        this.imgSrc = event.target.result;
                        this.$refs.cropper && this.$refs.cropper.replace(event.target.result);
                    }).catch(() => {
                        this.cropImg = event.target.result;
                    });
                    // 拿到图片的base64数据就好了，file input 可以清空了
                    this.$refs.imgFile.value = "";
                };
                reader.readAsDataURL(file);
            },
            cropImage () {
                this.cropImg = this.$refs.cropper.getCroppedCanvas().toDataURL();
            },
            cancelCrop(){
                this.dialogVisible = false;
                this.cropImg = this.imgSrc;
            },
            //将base64转换为文件
            dataURLtoFile(dataurl) {
                let arr = dataurl.split(","),
                    bstr = atob(arr[1]),
                    n = bstr.length,
                    u8arr = new Uint8Array(n);
                while (n--) {
                    u8arr[n] = bstr.charCodeAt(n);
                }
                return new File([u8arr], this.fileInfo.name, {
                    type: this.fileInfo.type
                });
            },
            async uploadImage(){
                if (!this.cropImg) {
                    return null;
                }
                if (this.cropImg.indexOf('base64') > 0) {
                    let file = this.dataURLtoFile(this.cropImg);
                    let retUrl = await this.$oss.upload(file, this.uploadPath);
                    if (retUrl) {
                        return retUrl;
                    }
                }

                return this.cropImg;
            },
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
        .avatar-uploader-icon {
            font-size: 28px;
            color: #8c939d;
            text-align: center;
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

</style>
