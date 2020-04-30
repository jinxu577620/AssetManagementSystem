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
                    <el-col :span="4">
                        <el-input v-model="query.aname" placeholder="资产名称"></el-input>
                    </el-col>
                    <el-col :span="4">
                        <el-input v-model="query.acname" placeholder="资产类别"></el-input>
                    </el-col>
                    <el-col :span="4">
                        <el-input v-model="query.uid" placeholder="所属用户"></el-input>
                    </el-col>
                    <el-col :span="4">
                        <el-input v-model="query.department" placeholder="所属部门"></el-input>
                    </el-col>
                    <el-col :span="3">
                        <el-input v-model="query.astate" placeholder="状态"></el-input>
                    </el-col>
                    <el-col :span="3">
                        <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
                        
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
      
                <el-table-column prop="aid" label="资产编号" align="center"></el-table-column>
                <el-table-column prop="aname" label="名称" width="90" align="center"></el-table-column>
                <el-table-column prop="acname" label="资产类别" width="90" align="center"></el-table-column>
                <el-table-column prop="num" label="数量" width="90" align="center"></el-table-column>
                <el-table-column prop="stime" label="入库时间" width="100" align="center"></el-table-column>
                <el-table-column label="价格" align="center" width="100">
                    <template slot-scope="scope">
                        <p>{{scope.row.price}} 元</p>
                    </template>
                </el-table-column>
                <el-table-column prop="astate" label="状态" width="100" align="center"></el-table-column>
                <el-table-column prop="etime" label="报废时间" width="100" align="center"></el-table-column>
                <el-table-column prop="uid" label="所属用户" align="center"></el-table-column>
                <el-table-column prop="department" label="所属部门" width="150" align="center"></el-table-column>
                <el-table-column prop="annex" label="附件" width="150" align="center"></el-table-column>
                <el-table-column prop="supplier" label="供应商" width="150" align="center"></el-table-column>
                <el-table-column prop="evaluate" label="评价" width="150" align="center"></el-table-column>
                <el-table-column prop="manufacturer" label="生产厂家" width="150" align="center"></el-table-column>
                <el-table-column prop="place" label="存放地点" width="150" align="center"></el-table-column>
                
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
    // 导出需要安装xlsx 与 file-saver依赖: npm install --save xlsx file-saver
    //import FileSaver from 'file-saver'
    //import XLSX from 'xlsx'
export default {
    //name: 'StageCourseList',
    name: 'AssetList',
    data() {
        return {
            query: {
                aname: '',
                acname: '',
                uid: '',
                department: '',
                astate: '',
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
            this.$apis.getAssets(this.query).then(res =>{
                this.tableData = res.data.records;
                this.format(this.tableData);
                this.pageTotal = res.data.total || 1;
            }).catch(err =>{
                this.$message.error(err);
            });
        },
        // 格式化日期函数
        format(records){
            for(let record of records){
                record.stime = moment(record.stime).format('YYYY-MM-DD');
                record.etime = moment(record.etime).format('YYYY-MM-DD');
            }
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
            if (moment(row.etime).subtract(3, 'days').isAfter(moment()) || row.astate==("报废")) {
                return 'success-row';
            } else if (moment(row.etime).isAfter(moment())) {
                return 'warning-row';
            }
            return 'dangerous-row';
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
    background: #ffc6c6;
}
</style>
