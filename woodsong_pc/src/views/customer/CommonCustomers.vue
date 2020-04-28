<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-calendar"></i> 用户管理
                </el-breadcrumb-item>
                <el-breadcrumb-item>客户池</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-input v-model="query.name" placeholder="客户名称" class="handle-input mr10"></el-input>
                <el-input v-model="query.tel" placeholder="客户手机" class="handle-input mr10"></el-input>
                <el-input v-model="query.wangwang" placeholder="客户旺旺号" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
            </div>
            <el-table
                :data="tableData"
                border
                class="table"
                ref="multipleTable"
            >
                <el-table-column label="详情" type="expand" align="center" width="50" fixed>
                    <template slot-scope="props">
                        <el-form label-position="left" inline class="demo-table-expand">
                            <el-form-item label="客户Id">
                                <span v-if="props.row.id">{{ props.row.id }}</span>
                                <span v-else>无</span>
                            </el-form-item>
                            <el-form-item label="过期时间">
                                <span v-if="props.row.overdueTime">{{ props.row.overdueTime }}</span>
                                <span v-else>无</span>
                            </el-form-item>
                            <el-form-item label="客户需求">
                                <span v-if="props.row.requirement">{{ props.row.requirement }}</span>
                                <span v-else>无</span>
                            </el-form-item>
                        </el-form>
                    </template>
                </el-table-column>
                <el-table-column prop="wangwang" label="旺旺号" width="120" align="center"></el-table-column>
                <el-table-column label="客户名" width="70" align="center">
                    <template slot-scope="scope">
                        <span v-if="scope.row.name">{{ scope.row.name }}</span>
                        <span v-else>无</span>
                    </template>
                </el-table-column>
                <el-table-column label="手机" width="120" align="center">
                    <template slot-scope="scope">
                        <span v-if="scope.row.tel">{{ scope.row.tel }}</span>
                        <span v-else>无</span>
                    </template>
                </el-table-column>
                <el-table-column label="微信" width="120" align="center">
                    <template slot-scope="scope">
                        <span v-if="scope.row.weixin">{{ scope.row.weixin }}</span>
                        <span v-else>无</span>
                    </template>
                </el-table-column>
                <el-table-column label="客户意向" align="center" width="150">
                    <template slot-scope="scope">
                        <el-rate
                                disabled
                                v-model="scope.row.intention"
                                :colors="colors">
                        </el-rate>
                    </template>
                </el-table-column>
                <el-table-column label="客户类型" align="center">
                    <template slot-scope="scope">
                        <span v-if="scope.row.type==='普通客户'">
                            <el-tag type="success">普通客户</el-tag>
                        </span>
                        <span v-else-if="scope.row.type==='刷单'">
                            <el-tag type="warning">刷单</el-tag>
                        </span>
                        <span v-else-if="scope.row.type==='已购客户'">
                            <el-tag type="info">已购客户</el-tag>
                        </span>
                        <span v-else>无</span>
                    </template>
                </el-table-column>
                <el-table-column label="来源平台" align="center">
                    <template slot-scope="scope">
                        <span v-if="scope.row.origin">{{ scope.row.origin }}</span>
                        <span v-else>无</span>
                    </template>
                </el-table-column>
                <el-table-column prop="saleName" label="前销售" align="center"></el-table-column>
                <el-table-column label="操作" align="center" v-if="user.role < 3">
                    <template slot-scope="scope">
                        <el-button
                            type="text"
                            icon="el-icon-edit"
                            @click="handleDeal(scope.$index, scope.row)"
                        >跟单</el-button>
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
export default {
    name: 'MyCustomers',
    data() {
        return {
            query: {
                name: '',
                tel: '',
                wangwang: '',
                pageIndex: 1,
                pageSize: 15
            },
            pageTotal: 0,
            tableData: [],
            idx: -1,        // 记录当前编辑行数，从0开始计
            colors: ['#99A9BF', '#F7BA2A', '#FF9900'],
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
            this.$apis.getCustomers(this.query).then(res =>{
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

        // 跟单操作
        handleDeal(index, row) {
            // 二次确认跟单
            this.$confirm('确定要对该用户进行跟单吗？', '提示', {
                type: 'warning'
            })
            .then(async () => {
                this.$apis.dealCustomer({id:row.id}).then(res =>{
                    this.$message.success('跟单成功');
                    this.$router.push("/myCustomers");
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
