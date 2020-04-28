<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-calendar"></i> 订单管理
                </el-breadcrumb-item>
                <el-breadcrumb-item>创建订单</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="form-box" style="width: 900px;margin: 0 auto">
                <el-form ref="form" :model="form" :rules="rules" label-width="100px">
                    <!--<el-form-item label="客户Id" prop="customerId">-->
                        <!--<el-input v-model="form.customerId"></el-input>-->
                    <!--</el-form-item>-->
                    <el-form-item label="订单编号" prop="ordersId">
                        <el-input v-model="form.ordersId"></el-input>
                    </el-form-item>
                    <el-form-item label="订单详情">
                        <my-vue-editor ref="editor" @change="onEditorChange" style="line-height: 24px;"></my-vue-editor>
                    </el-form-item>
                    <el-form-item label="地址" prop="address">
                        <el-input v-model="form.address"></el-input>
                    </el-form-item>
                    <el-form-item label="发货期限时间">
                        <el-date-picker
                                v-model="form.overdueTime"
                                type="datetime"
                                placeholder="选择日期时间"
                                align="right"
                                :picker-options="pickerOptions">
                        </el-date-picker>
                    </el-form-item>
                    <el-form-item label="订单金额">
                        <el-input v-model.number="form.price"></el-input>
                    </el-form-item>
                    <el-form-item label="运费类型">
                        <el-radio-group v-model="form.fareType">
                            <el-radio label="包邮">包邮</el-radio>
                            <el-radio label="到付">到付</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item label="运费金额">
                        <el-input v-model.number="form.fare"></el-input>
                    </el-form-item>
                    <el-form-item label="支付平台">
                        <el-radio-group v-model="form.payPlatform">
                            <el-radio label="天猫">天猫</el-radio>
                            <el-radio label="京东">京东</el-radio>
                            <el-radio label="东方红">东方红</el-radio>
                            <el-radio label="木佰年">木佰年</el-radio>
                            <el-radio label="微信">微信</el-radio>
                            <el-radio label="公司账户">公司账户</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item label="是否有尾款">
                        <el-radio-group v-model="form.tailed">
                            <el-radio label="0">没有</el-radio>
                            <el-radio label="1">有</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item label="尾款金额" prop="tailMoney">
                        <el-input v-model.number="form.tailMoney"></el-input>
                    </el-form-item>
                    <el-form-item label="赠品">
                        <el-radio-group v-model="form.gift">
                            <el-radio label="无">无</el-radio>
                            <el-radio label="六君子">六君子</el-radio>
                            <el-radio label="保养包">保养包</el-radio>
                            <el-radio label="烟灰缸">烟灰缸</el-radio>
                            <el-radio label="实木茶盘">实木茶盘</el-radio>
                            <el-radio label="手串">手串</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item label="备注">
                        <my-vue-editor ref="remarkEditor" @change="onEditorChange2" style="line-height: 24px;"></my-vue-editor>
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
    import MyVueEditor from '../../components/utils/MyVueEditor'
    import moment from 'moment'   // 日期处理类库
    export default {
        name: 'CreateUser',
        components: {
            MyVueEditor
        },
        computed: {
            customerId: function () {
                return this.$route.query.customerId;
            }
        },
        data() {
            return {
                form: {
                    overdueTime: moment().add(5, 'days').format('YYYY-MM-DD HH:mm:ss'),
                    price: 0,
                    fareType: '包邮',
                    fare: 0,
                    payPlatform: '',
                    tailed: '0',
                    tailMoney: 0,
                    gift: '无'
                },
                pickerOptions: {
                    shortcuts: [{
                        text: '今天',
                        onClick(picker) {
                            picker.$emit('pick', new Date());
                        }
                    }, {
                        text: '明天',
                        onClick(picker) {
                            const date = new Date();
                            date.setTime(date.getTime() + 3600 * 1000 * 24);
                            picker.$emit('pick', date);
                        }
                    }, {
                        text: '两天后',
                        onClick(picker) {
                            const date = new Date();
                            date.setTime(date.getTime() + 3600 * 1000 * 24 * 2);
                            picker.$emit('pick', date);
                        }
                    }, {
                        text: '三天后',
                        onClick(picker) {
                            const date = new Date();
                            date.setTime(date.getTime() + 3600 * 1000 * 24 * 3);
                            picker.$emit('pick', date);
                        }
                    }, {
                        text: '一周后',
                        onClick(picker) {
                            const date = new Date();
                            date.setTime(date.getTime() + 3600 * 1000 * 24 * 7);
                            picker.$emit('pick', date);
                        }
                    }]
                },
                rules: {
                    ordersId: [{ required: true, message: '请输入订单编号', trigger: 'blur' }],
                    address: [{ required: true, message: '请输入地址', trigger: 'blur' }],
                    customerTel: [{ required: true, message: '请输入手机', trigger: 'blur' }],
                },
            };
        },
        methods: {
            onEditorChange(html) {
                this.form.info = html;
            },
            onEditorChange2(html) {
                this.form.remark = html;
            },
            async onSubmit(formName) {
                this.$refs[formName].validate(async (valid) => {
                    if (valid) {
                        this.$set(this.form, "customerId", this.customerId);

                        if (this.form.overdueTime !== undefined) {
                            // 格式化时间格式
                            let date = moment(this.form.overdueTime).format('YYYY-MM-DD HH:mm:ss');
                            this.$set(this.form, "overdueTime", date);
                        }

                        // 清理富文本中上传了又删除的图片，并最后返回使用了的图片
                        let useImgs = this.$refs.editor.clearImg();
                        this.$set(this.form, "infoImg", useImgs.join(','));

                        let remarkUseImgs = this.$refs.remarkEditor.clearImg();
                        this.$set(this.form, "remarkImg", remarkUseImgs.join(','));

                        this.$apis.saveOrders(this.form).then(res =>{
                            this.$message.success('提交成功！');
                            if (this.form.price > 10000){
                                this.$router.push("/pendingApprovalOrders");
                            } else{
                                this.$router.push("/pendingSendOrders");
                            }

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
