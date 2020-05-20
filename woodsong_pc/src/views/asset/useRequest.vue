<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-calendar"></i> 资产使用申请
                </el-breadcrumb-item>
                <el-breadcrumb-item>资产使用申请</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            
            <el-table
                :data="tableData"
                border
                class="table"
                ref="multipleTable"
                :row-class-name="tableRowClassName"
            >
      
                <el-table-column prop="rid" label="申请编号" width="90" align="center"></el-table-column>
                <el-table-column prop="uid" label="申请人编号" width="90" align="center"></el-table-column>
                <el-table-column prop="uname" label="申请人姓名" width="90" align="center"></el-table-column>
                <el-table-column prop="aid" label="资产编号" width="90" align="center"></el-table-column>
                <el-table-column prop="aname" label="资产名称" width="100" align="center"></el-table-column>
                <el-table-column prop="ause" label="申请理由"  align="center"></el-table-column>
                
                <el-table-column prop="rstate" label="状态" width="150" align="center"
                    :filters="[{text: '已通过', value: '已通过'}, {text: '已拒绝', value: '已拒绝'}, {text: '审核中', value: '审核中'}]"
                    :filter-method="filterHandler"
                    column-key="rstate"
                >
                    <template slot-scope="scope">
                        <p>{{scope.row.rstate}} 
                        </p>
                    </template>
                </el-table-column>
                <el-table-column prop="department" label="所属部门" width="170" align="center"></el-table-column>
                
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
    //name: 'StageCourseList',
    name: 'AssetList',
    data() {
        return {
            query: {
                uid: "",
                amauthority: "",
                pageIndex: 1,
                pageSize: 15
            },
            get:{
                uid: '',
                uname: '',
                aid: '',
                aname: '',
                ause: '',
                department: '',
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
            let user = this.$store.state.userInfo.user;
            this.query.uid = user.uid;
            this.query.amauthority = user.amauthority;
            console.log(this.query.uid);
            console.log(this.query.amauthority);
            this.$apis.getUseRequest(this.query).then(res =>{
                this.tableData = res.data.records;

                this.pageTotal = res.data.total || 1;
            }).catch(err =>{
                this.$message.error(err);
            });
        },

       
        //按状态筛选
        filterHandler(value, row, column) {
            const property = column['property'];
            return row[property] === value;
        },
        // 分页导航
        handlePageChange(val) {
            this.$set(this.query, 'pageIndex', val);
            this.getData();
        },

       
    }
};
</script>
