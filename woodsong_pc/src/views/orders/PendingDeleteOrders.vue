<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-calendar"></i> 订单管理
                </el-breadcrumb-item>
                <el-breadcrumb-item>待删除订单</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-input v-model="query.ordersId" placeholder="订单号" class="handle-input mr10"></el-input>
                <el-input v-model="query.saleName" placeholder="销售员" class="handle-input mr10"></el-input>
                <el-input v-model="query.customerName" placeholder="客户名称" class="handle-input mr10"></el-input>
                <el-input v-model="query.customerWangwang" placeholder="客户旺旺" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
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
                <el-table-column prop="saleName" label="销售员" width="90" align="center"></el-table-column>
                <el-table-column prop="customerName" label="客户名" width="90" align="center"></el-table-column>
                <el-table-column prop="customerWangwang" label="客户旺旺" width="120" align="center"></el-table-column>
                <el-table-column prop="overdueTime" label="发货期限" align="center"></el-table-column>
                <el-table-column label="发货期限" width="100" align="center">
                    <template slot-scope="scope">
                        <el-tag v-if="remindStyle(scope.row.overdueTime)" type="success">{{remainDays(scope.row.overdueTime)}}</el-tag>
                        <el-tag v-else type="danger">{{remainDays(scope.row.overdueTime)}}</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="订单金额" align="center" width="100">
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
                            <el-tag type="warning">到付</el-tag>
                        </span>
                        <span v-else>无</span>
                    </template>
                </el-table-column>
                <el-table-column label="运费" align="center" width="80">
                    <template slot-scope="scope">
                        <p>{{scope.row.fare}} 元</p>
                    </template>
                </el-table-column>
                <el-table-column label="操作" align="center" v-if="user.role === 0">
                    <template slot-scope="scope">
                        <el-button
                            type="text"
                            icon="el-icon-edit"
                            @click="handleCancelDelete(scope.$index, scope.row)"
                        >取消删除</el-button>
                        <el-button
                                type="text"
                                icon="el-icon-delete"
                                class="red"
                                @click="handleDelete(scope.$index, scope.row)"
                        >确定删除</el-button>
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
    </div>
</template>

<script>
    import moment from 'moment'   // 日期处理类库
export default {
    name: 'StageCourseList',
    data() {
        return {
            query: {
                saleName: '',
                customerName: '',
                customerWangwang: '',
                ordersId: '',
                status: '待删除',
                pageIndex: 1,
                pageSize: 15
            },
            pageTotal: 0,
            tableData: [],
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

        // 取消删除操作
        handleCancelDelete(index, row) {
            // 二次确认删除
            this.$confirm('确定取消删除吗？', '提示', {
                type: 'warning'
            })
                .then(async () => {
                    this.$apis.cancelDeleteOrders({id:row.id}).then(res =>{
                        this.$message.success('申请成功');
                        this.$router.push("/pendingSendOrders");
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
