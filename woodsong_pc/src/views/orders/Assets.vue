<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-calendar"></i> 资产管理
                </el-breadcrumb-item>
                <el-breadcrumb-item>查看资产</el-breadcrumb-item>
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
       <!--         <el-table-column label="详情" type="expand" align="center" width="50">
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
                            <el-form-item label="物流信息">
                                <div v-if="props.row.logistics" v-html="props.row.logistics" class="richTextStyle"></div>
                                <span v-else>无</span>
                            </el-form-item>
                        </el-form>
                    </template>
           
                </el-table-column> -->
                <el-table-column prop="aid" label="资产编号" align="center"></el-table-column>
                <el-table-column prop="aname" label="名称" width="90" align="center"></el-table-column>
                <el-table-column prop="acname" label="资产类别" width="90" align="center"></el-table-column>
                <el-table-column prop="num" label="数量" width="90" align="center"></el-table-column>
                <el-table-column prop="stime" label="入库时间" width="120" align="center"></el-table-column>
                <el-table-column prop="price" label="价格" align="center"></el-table-column>
                <el-table-column prop="etime" label="报废时间" align="center"></el-table-column>
                <el-table-column prop="uid" label="所属用户" align="center"></el-table-column>
                <el-table-column prop="department" label="所属部门" align="center"></el-table-column>
                <el-table-column prop="annex" label="附件" align="center"></el-table-column>
                <el-table-column prop="supplier" label="供应商" align="center"></el-table-column>
                <el-table-column prop="evaluate" label="评价" align="center"></el-table-column>
                <el-table-column prop="manufacturer" label="生产厂家" align="center"></el-table-column>
                <el-table-column prop="place" label="存放地点" align="center"></el-table-column>
                <!--
                <el-table-column label="订单金额" align="center" width="100">
                    <template slot-scope="scope">
                        <p>{{scope.row.price}} 元</p>
                    </template>
                </el-table-column>
                <el-table-column label="运费" align="center" width="80">
                    <template slot-scope="scope">
                        <p>{{scope.row.fare}} 元</p>
                    </template>
                </el-table-column>
                -->
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
            <el-table-column label="订单类型">已发货</el-table-column>
            <el-table-column prop="shipperName" label="快递员"></el-table-column>
            <el-table-column prop="logistics" label="快递信息"></el-table-column>
            <el-table-column prop="customerName" label="客户名"></el-table-column>
            <el-table-column prop="customerWeixin" label="客户微信"></el-table-column>
            <el-table-column prop="saleTel" label="销售手机"></el-table-column>
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
    </div>
</template>

<script>
    import moment from 'moment'   // 日期处理类库
    // 导出需要安装xlsx 与 file-saver依赖: npm install --save xlsx file-saver
    import FileSaver from 'file-saver'
    import XLSX from 'xlsx'
export default {
    //name: 'StageCourseList',
    name: 'AssetList',
    data() {
        return {
            query: {
                //assetId: '',
                
                //customerName: '',
                //customerWangwang: '',
                //ordersId: '',
                //status: '',
                pageIndex: 1,
                pageSize: 3
            },
            pageTotal: 0,
            tableData: [],
            exportTableData: [],  // 导出的订单表单数据
            idx: -1,        // 记录当前编辑行数，从0开始计
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
            this.$apis.getAssets(this.query).then(res =>{
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
                        FileSaver.saveAs(new Blob([wbout], {type: 'application/octet-stream'}), '已发货订单.xlsx')
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
