<template>
    <quill-editor
            ref="myTextEditor"
            class="my-editor"
            v-model="currentContent"
            :options="editorOption"
            @change="onEditorChange($event)"
    ></quill-editor>
</template>

<script>
    import 'quill/dist/quill.core.css'
    import 'quill/dist/quill.snow.css'
    import 'quill/dist/quill.bubble.css'
    import { quillEditor } from 'vue-quill-editor'

    export default {
        name: "MyVueEditor",
        components: {
            quillEditor
        },
        props: {
            content: {        // 裁剪后的图片，给img进行显示
                type: String,
                default: ''
            }
        },
        data: function(){
            let that = this;
            return {
                currentContent: this.content,  // 第一次的时候赋值为第一次传进来的值
                imgUrls: [], // 富文本中上传的图片url，用于最后清除未使用图片
                editorOption: {
                    placeholder: '',
                    theme: 'snow',  // 主题
                    modules: {
                        toolbar: {
                            container: [  // toolbar工具栏的工具选项（默认展示全部）
                                ['bold', 'italic', 'underline', 'strike'],
                                ['blockquote', 'code-block'],
                                [{'header': 1}, {'header': 2}],
                                [{'list': 'ordered'}, {'list': 'bullet'}],
                                [{'script': 'sub'}, {'script': 'super'}],
                                [{'indent': '-1'}, {'indent': '+1'}],
                                [{'direction': 'rtl'}],
                                [{'size': ['small', false, 'large', 'huge']}],
                                [{'header': [1, 2, 3, 4, 5, 6, false]}],
                                [{'color': []}],
                                [{'background': []}],
                                [{'font': []}],
                                [{'align': []}],
                                ['clean'],
                                ['link', 'image', 'video']
                            ],
                            handlers: {    // 处理加载图片，视频等事件处理，这边修改图片上传加载
                                image: function (value) {
                                    if (value) {
                                        var self = this;

                                        // 这里添加文件上传input，之后click触发进行上传
                                        var fileInput = this.container.querySelector('input.ql-image[type=file]');
                                        if (fileInput === null) {
                                            fileInput = document.createElement('input');
                                            fileInput.setAttribute('type', 'file');

                                            // 可设置上传图片的格式
                                            fileInput.setAttribute('accept', 'image/png, image/gif, image/jpeg, image/bmp, image/x-icon');
                                            fileInput.classList.add('ql-image');
                                            // 监听选择文件
                                            fileInput.addEventListener('change', async function () {

                                                let retUrl = await that.$oss.upload(fileInput.files[0], 'editorImgs/');

                                                if (retUrl){
                                                    let length = self.quill.getSelection(true).index;
                                                    //这里很重要，你图片上传成功后，img的src需要在这里添加，retUrl就是你服务器返回的图片链接。
                                                    self.quill.insertEmbed(length, 'image', retUrl);
                                                    // 调整光标到最后
                                                    self.quill.setSelection(length + 1)

                                                    that.imgUrls.push(retUrl)  // 将上传的图片地址添加到imgUrls中
                                                }
                                            });
                                            this.container.appendChild(fileInput);
                                        }
                                        fileInput.click();
                                    } else {
                                        this.quill.format('image', false);
                                    }
                                }
                            }
                        }
                    }
                },
                titleConfig: {   //给菜单栏添加中文标题title配置
                    'ql-bold': '加粗',
                    'ql-color': '颜色',
                    'ql-font': '字体',
                    'ql-code': '插入代码',
                    'ql-italic': '斜体',
                    'ql-link': '添加链接',
                    'ql-background': '背景颜色',
                    'ql-size': '字体大小',
                    'ql-strike': '删除线',
                    'ql-script': '上标/下标',
                    'ql-underline': '下划线',
                    'ql-blockquote': '引用',
                    'ql-header': '标题',
                    'ql-indent': '缩进',
                    'ql-list': '列表',
                    'ql-align': '文本对齐',
                    'ql-direction': '文本方向',
                    'ql-code-block': '代码块',
                    'ql-formula': '公式',
                    'ql-image': '图片',
                    'ql-video': '视频',
                    'ql-clean': '清除字体样式'
                }
            }
        },
        watch: {
            // 创建组件完成之后，需要监听传进来的content的变化，从而改变之后的content的初始化
            content: function () {
                this.currentContent = this.content;
                this.imgUrls = this.getContentImgs();
            }
        },
        mounted() {
            this.addQuillTitle();
            this.imgUrls = this.getContentImgs();   // 创建组件的时候，获取传进来的content中的imgUrls
        },
        methods: {
            // 给菜单栏添加中文标题
            addQuillTitle () {
                let _this = this
                const oToolBar = document.querySelector('.ql-toolbar'),
                    aButton = oToolBar.querySelectorAll('button'),
                    aSelect = oToolBar.querySelectorAll('select')
                aButton.forEach(function (item) {
                    if (item.className === 'ql-script') {
                        item.value === 'sub' ? item.title = '下标' : item.title = '上标'
                    } else if (item.className === 'ql-indent') {
                        item.value === '+1' ? item.title = '向右缩进' : item.title = '向左缩进'
                    } else {
                        item.title = _this.titleConfig[item.classList[0]]
                    }
                })
                aSelect.forEach(function (item) {
                    item.parentNode.title = _this.titleConfig[item.classList[0]]
                })
            },
            getContentImgs(){
                // 首先获取所有使用的图片url
                let dom = document.createElement('DIV')
                dom.innerHTML = this.currentContent
                const imgDom = dom.getElementsByTagName('img')
                let useImgs = [];
                for (let i = 0; i < imgDom.length; i++) {
                    useImgs.push(imgDom[i].src);
                }
                return useImgs;
            },
            // 清理已上传，但是又在富文本中删除的图片
            clearImg(){
                let useImgs = this.getContentImgs();
                let a=new Set(useImgs);
                let b=new Set(this.imgUrls);
                // 获取已删除的图片url
                let removeImgs = Array.from(
                    new Set(
                        [...b].filter(x => !a.has(x))
                            .map(y => {return y.replace(process.env.VUE_APP_OSS_URL,"")})
                    )
                );
                if (removeImgs.length > 0)
                    this.$oss.deleteMulti(removeImgs);
                else {
                    console.log("没有去掉已上传的图片")
                }
                return useImgs;
            },
            // 将编辑内容传给父组件
            onEditorChange({ editor, html, text }) {
                this.$emit('change', html)
            }

        }

    }
</script>

<style scoped>
    .my-editor >>> .ql-container{
        height: 400px;
        overflow-y:scroll;
    }
</style>
