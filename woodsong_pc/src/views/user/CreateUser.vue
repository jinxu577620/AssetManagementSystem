<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-calendar"></i> 用户管理
                </el-breadcrumb-item>
                <el-breadcrumb-item>创建用户</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="form-box" style="width: 900px;margin: 0 auto">
                <el-form ref="form" :model="form" :rules="rules" label-width="100px">
                    <el-form-item label="用户头像">
                        <my-vue-upload-cropper
                                ref="headImg"
                                :imgWidth="120"
                                :imgHeight="120"
                                :uploadPath="'user/'"
                        ></my-vue-upload-cropper>
                    </el-form-item>
                    <el-form-item label="名称" prop="name">
                        <el-input v-model="form.name"></el-input>
                    </el-form-item>
                    <el-form-item label="手机" prop="tel">
                        <el-input v-model="form.tel"></el-input>
                    </el-form-item>
                    <el-form-item label="密码" prop="password">
                        <el-input v-model="form.password"></el-input>
                    </el-form-item>
                    <el-form-item label="邮箱" prop="email">
                        <el-input v-model="form.email"></el-input>
                    </el-form-item>
                    <el-form-item label="角色">
                        <el-radio-group v-model="form.role">
                            <el-radio label="0">管理员</el-radio>
                            <el-radio label="1">财务</el-radio>
                            <el-radio label="2">销售员</el-radio>
                            <el-radio label="3">发货员</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item v-if="form.role==='2'" label="销售旺旺" prop="wangwang">
                        <el-input v-model="form.wangwang" placeholder="多个用逗号分隔"></el-input>
                    </el-form-item>

                    <el-form-item>
                        <el-button type="primary" @click="onSubmit('form')">表单提交</el-button>
                        <el-button @click="resetForm('form')">取消</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>
    </div>
</template>

<script>
    import MyVueUploadCropper from '../../components/utils/MyVueUploadCropper'
    export default {
        name: 'CreateUser',
        components: {
            MyVueUploadCropper
        },
        data() {
            return {
                form: {
                    name: '',
                    password: '',
                    tel: '',
                    email: '',
                    role: 0,
                    wangwang: ''
                },
                rules: {
                    name: [{ required: true, message: '请输入名称', trigger: 'blur' }],
                    password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
                    tel: [{ required: true, message: '请输入手机', trigger: 'blur' }],
                    weight: [
                        { required: true, message: '请输入权重', trigger: 'blur' },
                        { type: 'number', message: '权重必须为数字值', trigger: 'blur'}
                    ],
                },
            };
        },
        methods: {
            async onSubmit(formName) {
                this.$refs[formName].validate(async (valid) => {
                    if (valid) {
                        // 保存修改或添加

                        let headImg = await this.$refs.headImg.uploadImage();
                        if (!headImg) {
                            // this.$message.error('请先上传头像');
                            // return;
                        }else{
                            this.$set(this.form, "headImgUrl", headImg);
                        }
                        if (this.form.password !== "") {
                            this.$set(this.form, "password", this.$md5(this.form.password));
                        }

                        this.$apis.saveUser(this.form).then(res =>{
                            this.$message.success('提交成功！');
                            this.$router.push("/userList");
                        }).catch(err =>{
                            this.$message.error(err);
                        });
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },
        }
    };
</script>
