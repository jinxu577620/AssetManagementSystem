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
                <el-input v-model="query.ordersId" placeholder="订单号" class="handle-input mr10"></el-input>
                <el-input v-model="query.saleName" placeholder="销售员" class="handle-input mr10"></el-input>
                <el-input v-model="query.customerName" placeholder="客户名称" class="handle-input mr10"></el-input>
                <el-input v-model="query.customerWangwang" placeholder="客户旺旺" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
                <el-button type="primary" icon="el-icon-download" class="handle-del" @click="exportExcel">导出</el-button>
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
                            <el-form-item label="客户旺旺">
                                <span v-if="props.row.customerWangwang">{{ props.row.customerWangwang }}</span>
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
                <el-table-column prop="saleName" label="销售员" width="90" align="center"></el-table-column>
                <el-table-column prop="customerName" label="客户名" width="90" align="center"></el-table-column>
                <el-table-column prop="saleTel" label="销售手机" width="120" align="center"></el-table-column>
                <el-table-column prop="address" label="客户地址" width="200" align="center"></el-table-column>
                <el-table-column label="发货期限" width="100" align="center">
                    <template slot-scope="scope">
                        <el-tag v-if="remindStyle(scope.row.overdueTime)" type="success">{{remainDays(scope.row.overdueTime)}}</el-tag>
                        <el-tag v-else type="danger">{{remainDays(scope.row.overdueTime)}}</el-tag>
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
                <el-table-column label="运费" align="center" width="70">
                    <template slot-scope="scope">
                        <p>{{scope.row.fare}} 元</p>
                    </template>
                </el-table-column>
                <el-table-column label="操作" align="center" width="100">
                    <template slot-scope="scope">
                        <el-button
                            type="text"
                            icon="el-icon-edit"
                            @click="handleSend(scope.$index, scope.row)"
                        >发货</el-button>
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
            <el-table-column prop="ordersId" label="订单id" align="center"></el-table-column>
            <el-table-column prop="saleName" label="销售员" width="90" align="center"></el-table-column>
            <el-table-column prop="customerName" label="客户名" width="90" align="center"></el-table-column>
            <el-table-column prop="saleTel" label="销售手机" width="120" align="center"></el-table-column>
            <el-table-column prop="address" label="客户地址" width="200" align="center"></el-table-column>
            <el-table-column label="发货期限" width="100" align="center">
                <template slot-scope="scope">
                    <el-tag v-if="remindStyle(scope.row.overdueTime)" type="success">{{remainDays(scope.row.overdueTime)}}</el-tag>
                    <el-tag v-else type="danger">{{remainDays(scope.row.overdueTime)}}</el-tag>
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
            <el-table-column label="运费" align="center" width="70">
                <template slot-scope="scope">
                    <p>{{scope.row.fare}} 元</p>
                </template>
            </el-table-column>
            <el-table-column label="备注">
                <template slot-scope="scope">
                    <div v-if="scope.row.remark" v-html="scope.row.remark" class="richTextStyle"></div>
                    <span v-else>无</span>
                </template>
            </el-table-column>
        </el-table>

        <!-- 编辑弹出框 -->
        <el-dialog title="编辑" :visible.sync="editVisible" width="780px">
            <el-form ref="form" :model="form" label-width="100px">
                <el-form-item label="物流信息">
                    <my-vue-editor
                            ref="editor"
                            :content="form.logistics"
                            @change="onEditorChange"
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
    import FileSaver from 'file-saver'
    import XLSX from 'xlsx'
export default {
    name: 'PendingDeliverOrders',
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
            form: {},
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
        // 编辑
        handleSend(index, row) {
            if (this.user.role !== 3) {
                this.$message.success('快递员才能发货');
                return;
            }
            this.idx = index;
            this.$set(this.form, "id", row.id);
            this.$set(this.form, "logistics", row.logistics);
            this.editVisible = true;
        },
        // 富文本直接修改到this.form.info中
        onEditorChange(html) {
            this.form.logistics = html;
        },

        submitForm(formName) {
            this.$refs[formName].validate(async (valid) => {
                if (valid) {
                    // 清理富文本中上传了又删除的图片，并最后返回使用了的图片
                    let useImgs = this.$refs.editor.clearImg();
                    this.$set(this.form, "logisticsImg", useImgs.join(','));

                    this.$apis.deliver(this.form).then(res =>{
                        this.editVisible = false;
                        this.$message.success('发货成功');
                        this.$router.push("/shippedOrders");
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

        async exportExcel() {
            // 加载要导出的数据，如果用显示表格中数据，分页不完全，所以用另一个表格加载
            this.$apis.getOrders({
                saleName: this.query.saleName,
                customerName: this.query.customerName,
                customerWangwang: this.query.customerWangwang,
                ordersId: this.query.ordersId,
                status: '待发货',
                pageIndex: 1,
                pageSize: 2000
            }).then(res =>{
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
                        FileSaver.saveAs(new Blob([wbout], {type: 'application/octet-stream'}), '物流待发货.xlsx')
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
    width: 220px;
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
