<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-calendar"></i> 订单管理
                </el-breadcrumb-item>
                <el-breadcrumb-item>待发货订单</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-row :gutter="20">
                    <el-col :span="5">
                        <el-input v-model="query.ordersId" placeholder="订单号"></el-input>
                    </el-col>
                    <el-col :span="4">
                        <el-input v-model="query.saleName" placeholder="销售员"></el-input>
                    </el-col>
                    <el-col :span="4">
                        <el-input v-model="query.customerName" placeholder="客户名称"></el-input>
                    </el-col>
                    <el-col :span="5">
                        <el-input v-model="query.customerWangwang" placeholder="客户旺旺"></el-input>
                    </el-col>
                    <el-col :span="6">
                        <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
                        <el-button
                                type="primary"
                                icon="el-icon-download"
                                class="handle-del"
                                @click="exportExcel"
                        >导出</el-button>
                    </el-col>
                </el-row>

            </div>
            <el-table
                :data="tableData"
                border
                class="table"
                ref="multipleTable"
                :row-class-name="tableRowClassName"
            >
                <el-table-column label="详情" type="expand" align="center" width="50">
                    <template slot-scope="props">
                        <el-form label-position="left" inline class="demo-table-expand">
                            <el-form-item label="客户微信">
                                <span v-if="props.row.customerWeixin">{{ props.row.customerWeixin }}</span>
                                <span v-else>无</span>
                            </el-form-item>
                            <el-form-item label="客户手机">
                                <span v-if="props.row.customerTel">{{ props.row.customerTel }}</span>
                                <span v-else>无</span>
                            </el-form-item>
                            <el-form-item label="客户地址">
                                <span v-if="props.row.address">{{ props.row.address }}</span>
                                <span v-else>无</span>
                            </el-form-item>
                            <el-form-item label="订单详情">
                                <div v-if="props.row.info" v-html="props.row.info" class="richTextStyle"></div>
                                <span v-else>无</span>
                            </el-form-item>
                            <el-form-item label="支付平台">
                                <span v-if="props.row.payPlatform">{{ props.row.payPlatform }}</span>
                                <span v-else>无</span>
                            </el-form-item>
                            <el-form-item label="是否有尾款">
                                <span v-if="props.row.tailed===1">是</span>
                                <span v-else>否</span>
                            </el-form-item>
                            <el-form-item label="尾款金额" v-if="props.row.tailed===1">
                                <span>{{ props.row.tailMoney}} 元</span>
                            </el-form-item>
                            <el-form-item label="赠品">
                                <span v-if="props.row.gift">{{ props.row.gift }}</span>
                                <span v-else>无</span>
                            </el-form-item>
                            <el-form-item label="备注">
                                <div v-if="props.row.remark" v-html="props.row.remark" class="richTextStyle"></div>
                                <span v-else>无</span>
                            </el-form-item>
                        </el-form>
                    </template>
                </el-table-column>
                <el-table-column prop="ordersId" label="订单id" align="center"></el-table-column>
                <el-table-column prop="saleName" label="销售员" width="80" align="center"></el-table-column>
                <el-table-column prop="customerName" label="客户名" width="80" align="center"></el-table-column>
                <el-table-column prop="customerWangwang" label="客户旺旺" width="120" align="center"></el-table-column>
                <el-table-column label="发货期限" width="100" align="center">
                    <template slot-scope="scope">
                        <el-tag v-if="remindStyle(scope.row.overdueTime)" type="success">{{remainDays(scope.row.overdueTime)}}</el-tag>
                        <el-tag v-else type="danger">{{remainDays(scope.row.overdueTime)}}</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="订单金额" align="center" width="120">
                    <template slot-scope="scope">
                        <p>{{scope.row.price}} 元</p>
                    </template>
                </el-table-column>
                <el-table-column label="运费类型" align="center" width="100">
                    <template slot-scope="scope">
                        <span v-if="scope.row.fareType==='包邮'">
                            <el-tag type="success">包邮</el-tag>
                        </span>
                        <span v-else-if="scope.row.fareType==='到付'">
                            <el-tag type="danger">到付</el-tag>
                        </span>
                        <span v-else>无</span>
                    </template>
                </el-table-column>
                <el-table-column label="运费" align="center" width="90">
                    <template slot-scope="scope">
                        <p>{{scope.row.fare}} 元</p>
                    </template>
                </el-table-column>
                <el-table-column label="操作" align="center" v-if="user.role < 3">
                    <template slot-scope="scope">
                        <el-button
                            type="text"
                            icon="el-icon-edit"
                            @click="handleEdit(scope.$index, scope.row)"
                        >编辑</el-button>
                        <el-button
                                v-if="user.role === 0"
                                type="text"
                                icon="el-icon-delete"
                                class="red"
                                @click="handleDelete(scope.$index, scope.row)"
                        >删除</el-button>
                        <el-button
                                v-if="user.role === 2"
                                type="text"
                                icon="el-icon-delete"
                                class="red"
                                @click="handleApplyDelete(scope.$index, scope.row)"
                        >申请删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination
                    background
                    layout="total, prev, pager, next"
                    :current-page="query.pageIndex"
                    :page-size="query.pageSize"
                    :total="pageTotal"
                    @current-change="handlePageChange"
                ></el-pagination>
            </div>
        </div>

        <!--导出的订单信息-->
        <el-table id="exportTable" :data="exportTableData" style="display: none;">
            <el-table-column prop="ordersId" label="订单id"></el-table-column>
            <el-table-column label="订单类型">待发货</el-table-column>
            <el-table-column label="订单详情">
                <template slot-scope="scope">
                    <div v-html="scope.row.info" class="richTextStyle"></div>
                </template>
            </el-table-column>
            <el-table-column prop="customerName" label="客户名"></el-table-column>
            <el-table-column prop="customerWeixin" label="客户微信"></el-table-column>
            <el-table-column prop="customerTel" label="客户手机"></el-table-column>
            <el-table-column prop="customerWangwang" label="客户旺旺"></el-table-column>
            <el-table-column prop="saleName" label="销售员"></el-table-column>
            <el-table-column prop="overdueTime" label="发货期限"></el-table-column>
            <el-table-column prop="price" label="订单金额"></el-table-column>
            <el-table-column prop="fareType" label="运费类型"></el-table-column>
            <el-table-column prop="fare" label="运费"></el-table-column>
            <el-table-column prop="address" label="客户地址"></el-table-column>
            <el-table-column prop="payPlatform" label="支付平台"></el-table-column>
            <el-table-column prop="tailed" label="是否有尾款"></el-table-column>
            <el-table-column prop="tailMoney" label="尾款金额"></el-table-column>
            <el-table-column prop="gift" label="赠品"></el-table-column>
        </el-table>

        <!-- 编辑弹出框 -->
        <el-dialog title="编辑" :visible.sync="editVisible" width="780px">
            <el-form ref="form" :model="form" :rules="rules" label-width="100px">
                <el-form-item label="客户Id" prop="customerId">
                    <el-input v-model="form.customerId"></el-input>
                </el-form-item>
                <el-form-item label="订单编号" prop="ordersId">
                    <el-input v-model="form.ordersId"></el-input>
                </el-form-item>
                <el-form-item label="地址" prop="address">
                    <el-input v-model="form.address"></el-input>
                </el-form-item>
                <el-form-item label="订单详情">
                    <my-vue-editor
                            ref="editor"
                            :content="form.info"
                            @change="onEditorChange"
                            style="line-height: 24px;"
                    ></my-vue-editor>
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
                        <el-radio :label="0">没有</el-radio>
                        <el-radio :label="1">有</el-radio>
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
                <el-form-item label="备注" prop="remark">
                    <my-vue-editor
                            ref="remarkEditor"
                            :content="form.remark"
                            @change="onEditorChange2"
                            style="line-height: 24px;"
                    ></my-vue-editor>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="resetForm('form')">取 消</el-button>
                <el-button type="primary" @click="submitForm('form')">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    import MyVueEditor from '../../components/utils/MyVueEditor'
    import moment from 'moment'   // 日期处理类库
    // 导出需要安装xlsx 与 file-saver依赖: npm install --save xlsx file-saver
    import FileSaver from 'file-saver'
    import XLSX from 'xlsx'
export default {
    name: 'StageCourseList',
    components: {
        MyVueEditor,
    },
    data() {
        return {
            query: {
                saleName: '',
                customerName: '',
                customerWangwang: '',
                ordersId: '',
                status: '待发货',
                pageIndex: 1,
                pageSize: 15
            },
            pageTotal: 0,
            tableData: [],
            exportTableData: [],  // 导出的订单表单数据
            idx: -1,        // 记录当前编辑行数，从0开始计

            editVisible: false,
            form: { },
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
                customerId: [{ required: true, message: '请输入名称', trigger: 'blur' }],
                ordersId: [{ required: true, message: '请输入订单编号', trigger: 'blur' }],
                address: [{ required: true, message: '请输入地址', trigger: 'blur' }],
                customerTel: [{ required: true, message: '请输入手机', trigger: 'blur' }],
            },
        };
    },
    computed: {
        user() {
            let user = this.$store.state.userInfo.user;
            return user ? user : null;
        }
    },
    created() {
        this.getData();
    },
    activated() {
        this.getData();
    },
    methods: {
        async getData() {
            this.$apis.getOrders(this.query).then(res =>{
                this.tableData = res.data.records;
                this.pageTotal = res.data.total || 1;
            }).catch(err =>{
                this.$message.error(err);
            });
        },
        // 触发搜索按钮
        handleSearch() {
            this.$set(this.query, 'pageIndex', 1);
            this.getData();
        },
        // 分页导航
        handlePageChange(val) {
            this.$set(this.query, 'pageIndex', val);
            this.getData();
        },
        // 保护剩余天数样式提示
        remindStyle(val) {
            if (moment(val).diff(moment(), 'day') > 0) return true;
            return false;
        },
        // 计算剩余天数
        remainDays(val) {
            let days = moment(val).diff(moment(), 'day');
            if (days > 0) return '剩 '+(days+1)+' 天';
            else if (days === 0){
                let hours = moment(val).diff(moment(), 'hour');
                if (hours >= 0) return '剩 '+hours+' 时';
                else return '超 '+(-hours)+' 时';
            }
            else return '超 '+(-days)+' 天';
        },
        // 根据过期日期，区分表格状态
        tableRowClassName({row, rowIndex}) {
            if (moment(row.overdueTime).subtract(3, 'days').isAfter(moment())) {
                return 'success-row';
            } else if (moment(row.overdueTime).isAfter(moment())) {
                return 'warning-row';
            }
            return 'dangerous-row';
        },


        async exportExcel() {
            // 加载要导出的数据，如果用显示表格中数据，分页不完全，所以用另一个表格加载
            this.$apis.getExportOrders(this.query).then(res =>{
                this.exportTableData = res.data.records;
                let loading = this.$loading({
                    lock: true,
                    text: 'Loading',
                    spinner: 'el-icon-loading',
                    background: 'rgba(0, 0, 0, 0.7)'
                });
                setTimeout(()=>{
                    // 从表格中生成workbook对象
                    let wb = XLSX.utils.table_to_book(document.querySelector('#exportTable'))
                    // get binary string as output
                    let wbout = XLSX.write(wb, {bookType: 'xlsx', bookSST: true, type: 'array'})
                    try {
                        FileSaver.saveAs(new Blob([wbout], {type: 'application/octet-stream'}), '待发货订单.xlsx')
                    } catch (e) {
                        if (typeof console !== 'undefined') console.log(e, wbout)
                    }
                    loading.close();
                    return wbout
                },1500);
            }).catch(err =>{
                this.$message.error(err);
            });
        },


        // 编辑
        handleEdit(index, row) {
            this.idx = index;
            this.form = row;
            this.editVisible = true;
        },
        // 富文本直接修改到this.form.info中
        onEditorChange(html) {
            this.form.info = html;
        },
        onEditorChange2(html) {
            this.form.remark = html;
        },

        submitForm(formName) {
            this.$refs[formName].validate(async (valid) => {
                if (valid) {
                    // 格式化时间格式
                    let date = moment(this.form.overdueTime).format('YYYY-MM-DD HH:mm:ss');
                    this.$set(this.form, "overdueTime", date);

                    // 清理富文本中上传了又删除的图片，并最后返回使用了的图片
                    let useImgs = this.$refs.editor.clearImg();
                    this.$set(this.form, "infoImg", useImgs.join(','));

                    let remarkUseImgs = this.$refs.remarkEditor.clearImg();
                    this.$set(this.form, "remarkImg", remarkUseImgs.join(','));

                    this.$apis.saveOrders(this.form).then(res =>{
                        this.$message.success(`修改第 ${this.idx + 1} 行成功`);
                        this.$set(this.tableData, this.idx, res.data);
                        this.editVisible = false;
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
            // 如果执行下面这句，会编辑一行再编辑另一行会覆盖另一行的信息
            // this.$refs[formName].resetFields();
            this.editVisible = false;
        },

        // 删除操作
        handleDelete(index, row) {
            // 二次确认删除
            this.$confirm('确定要删除吗？', '提示', {
                type: 'warning'
            })
            .then(async () => {
                this.$apis.removeOrders({id:row.id}).then(res =>{
                    this.$message.success('删除成功');
                    this.tableData.splice(index, 1);
                }).catch(err =>{
                    this.$message.error(err);
                });
            })
            .catch(() => {});
        },

        // 申请删除操作
        handleApplyDelete(index, row) {
            // 二次确认删除
            this.$confirm('确定需要申请删除吗？', '提示', {
                type: 'warning'
            })
                .then(async () => {
                    this.$apis.applyDeleteOrders({id:row.id}).then(res =>{
                        this.$message.success('申请成功');
                        this.$router.push("/pendingDeleteOrders");
                    }).catch(err =>{
                        this.$message.error(err);
                    });
                })
                .catch(() => {});
        },
    }
};
</script>

<style scoped>
.handle-box {
    margin-bottom: 20px;
}

.handle-select {
    width: 120px;
}

.handle-input {
    width: 230px;
    display: inline-block;
}
.table {
    width: 100%;
    font-size: 14px;
}
.red {
    color: #ff0000;
}
.mr10 {
    margin-right: 10px;
}
.table-td-face {
    display: block;
    margin: auto;
    width: 70px;
}
.table-td-big-face {
    display: block;
    margin: auto;
    width: 160px;
}
.richTextStyle{
    width: 800px;
    max-height: 400px;
    overflow-y:scroll;
    margin: 0 auto;
}
/*.richTextStyle >>> img{*/
    /*width: 325px;*/
/*}*/
.remarksTitle{
    width: 325px;
    margin: 20px auto;
}
.remarksTextStyle{
    width: 325px;
    margin: 0 auto;
}

.demo-table-expand {
    font-size: 0;
}
.demo-table-expand >>> label {
    width: 90px;
    color: #99a9bf;
}
.demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 100%;
}
.table >>> .warning-row {
    background: oldlace;
}

.table >>> .success-row {
    background: #f0f9eb;
}

.table >>> .dangerous-row {
    background: #fdb2b2;
}
</style>
