<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-calendar"></i> 用户管理
                </el-breadcrumb-item>
                <el-breadcrumb-item>创建客户</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="form-box" style="width: 900px;margin: 0 auto">
                <el-form ref="form" :model="form" :rules="rules" label-width="100px">
                    <el-form-item label="旺旺号" prop="wangwang">
                        <el-input v-model="form.wangwang"></el-input>
                    </el-form-item>
                    <el-form-item label="客户姓名" prop="name">
                        <el-input v-model="form.name"></el-input>
                    </el-form-item>
                    <el-form-item label="手机" prop="tel">
                        <el-input v-model="form.tel"></el-input>
                    </el-form-item>
                    <el-form-item label="微信号" prop="weixin">
                        <el-input v-model="form.weixin"></el-input>
                    </el-form-item>
                    <el-form-item label="是否加了微信">
                        <el-radio-group v-model="form.addWeiXin">
                            <el-radio :label="0">未添加</el-radio>
                            <el-radio :label="1">已添加</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item label="客户意向">
                        <el-rate
                                v-model="form.intention"
                                :colors="colors"
                                style="margin-top: 10px"
                        >
                        </el-rate>
                    </el-form-item>
                    <el-form-item label="客户类型">
                        <el-radio-group v-model="form.type">
                            <el-radio label="普通客户">普通客户</el-radio>
                            <el-radio label="刷单">刷单</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item label="是否购买">
                        <el-radio-group v-model="form.purchased">
                            <el-radio :label="0">未购买</el-radio>
                            <el-radio :label="1">已购买</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item label="来源平台">
                        <el-radio-group v-model="form.origin">
                            <el-radio label="天猫">天猫</el-radio>
                            <el-radio label="京东">京东</el-radio>
                            <el-radio label="东方红">东方红</el-radio>
                            <el-radio label="木佰年">木佰年</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item label="客户需求">
                        <el-input type="textarea" v-model="form.requirement"></el-input>
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
    export default {
        name: 'CreateCustomer',
        data() {
            return {
                form: {
                    name: '',
                    tel: '',
                    weixin: '',
                    addWeiXin: 0,
                    intention: 0,
                    type: '普通客户',
                    purchased: 0,
                    origin: '',
                    requirement: ''
                },
                colors: ['#99A9BF', '#F7BA2A', '#FF9900'],
                rules: {
                    wangwang: [{ required: true, message: '请输入旺旺号', trigger: 'blur' }]
                },
            };
        },
        methods: {
            async onSubmit(formName) {
                this.$refs[formName].validate(async (valid) => {
                    if (valid) {
                        // 保存修改或添加

                        this.$apis.saveCustomer(this.form).then(res =>{
                            this.$message.success('提交成功！');
                            this.$router.push("/myCustomers");
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
