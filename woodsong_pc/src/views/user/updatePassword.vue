<template>
<el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
    <el-form-item label="旧密码" prop="oldpass">
        <el-input type="password" v-model="ruleForm.oldpass"></el-input>
    </el-form-item>
    <el-form-item label="新密码" prop="pass">
        <el-input type="password" v-model="ruleForm.pass" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="再次确认密码" prop="checkPass">
        <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
    </el-form-item>
</el-form>
</template>
<script>
    export default {
        data() {
            var checkPassword = (rule, value, callback) => {
                if (!value) {
                    return callback(new Error('密码不能为空'));
                }
                callback();
            };
            var validatePass = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入新密码'));
                } else {
                    if (this.ruleForm.checkPass !== '') {
                        this.$refs.ruleForm.validateField('checkPass');
                    }
                    callback();
                }
            };
            var validatePass2 = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请再次输入新密码'));
                } else if (value !== this.ruleForm.pass) {
                    callback(new Error('两次输入密码不一致!'));
                } else {
                    callback();
                }
            };
            return {
                ruleForm: {
                    pass: '',
                    checkPass: '',
                    oldpass: '',
                    id:''
                },
                rules: {
                    pass: [
                        { validator: validatePass, trigger: 'blur' }
                    ],
                    checkPass: [
                        { validator: validatePass2, trigger: 'blur' }
                    ],
                    oldpass: [
                        { validator: checkPassword, trigger: 'blur' }
                    ]
                }
            };
        },
        methods: {
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.$set(this.ruleForm, "oldpass", this.$md5(this.ruleForm.oldpass));
                        this.$set(this.ruleForm, "pass", this.$md5(this.ruleForm.pass));
                        this.$set(this.ruleForm, "checkPass", this.$md5(this.ruleForm.checkPass));
                        this.$set(this.ruleForm, "id", this.user.uid);
                        //提交异步请求
                        this.$apis.updatePasswprd(this.ruleForm).then(res =>{
                            this.$message.success('修改成功！');
                            this.$router.push("/");
                            this.$refs[formName].resetFields();
                        }).catch(err =>{
                            this.$message.error(err);
                        });
                        alert('submit!');
                    } else {
                        alert('error submit!');
                        return false;
                    }
                });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            }
        },
        name:"updatePasswprd",
        computed: {
            user() {
                let user = this.$store.state.userInfo.user;
                return user ? user : null;
            }
        }
    }
</script>