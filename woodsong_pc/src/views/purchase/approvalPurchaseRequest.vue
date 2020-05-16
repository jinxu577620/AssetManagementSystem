<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-calendar"></i> 审批采购申请
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>

        <div class="container">
            <el-table
                    :data="tableData"
                    border
                    stripe
                    class="table"
                    ref="multipleTable"
                    header-cell-class-name="table-header"
            >
                <el-table-column prop="prid" label="申请编号" width="80" align="center" ></el-table-column>
                <el-table-column prop="aname" label="资产名称" align="center" ></el-table-column>
                <el-table-column prop="aclass" label="资产分类" align="center" ></el-table-column>
                <el-table-column prop="num" label="数量" align="center" ></el-table-column>
                <el-table-column prop="budget" label="预算" align="center" ></el-table-column>
                <el-table-column prop="uid" label="申请人工号" align="center" ></el-table-column>
                <el-table-column prop="uname" label="申请人姓名" align="center" ></el-table-column>
                <el-table-column prop="pmname" label="采购方式名称" align="center" ></el-table-column>
                <el-table-column prop="rstate" label="状态" align="center" ></el-table-column>
                <el-table-column prop="pid" label="采购流程编号" align="center" ></el-table-column>
                <el-table-column prop="specification" label="详细描述" align="center" ></el-table-column>
                <el-table-column prop="manufacturer" label="生产厂家" align="center" ></el-table-column>
                
                
                <el-table-column label="操作" align="center" >
                    <template slot-scope="scope">
                        <p v-if="scope.row.rstate==$store.state.userInfo.user.uid+'审核中'">
                            <el-button
                                    type="text"
                                    @click="handleAgree(scope.$index, scope.row)"
                            >通过</el-button>
                            <el-button
                                    type="text"
                                    class="red"
                                    @click="handleRefuse(scope.$index, scope.row)"
                            >拒绝</el-button>
                        </p>
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
        name: 'purchaseRequest',
        data() {
            return {
                query: {
                    aname:'',
                    pageIndex: 1,
                    pageSize: 15
                },
                pageTotal: 0,
                tableData: {
                    prid: "",      //自动
                    aname : "",     //输入
                    aclass: "",     //选
                    acid: "",
                    num: "",            //输入
                    budget: "",         //输入
                    uid: "",            //自动
                    uname: "",      //自动
                    pmid: "",
                    pmname: "",     //选
                    rstate: "",     //自动
                    pid: "",        //选
                    specification: "",  //输入
                    manufacturer: "",   //输入
                },
                pData:{       //从这个里面选流程
                    pid: '',
                    pname: '',
                    uid1: '',
                    uid2: '',
                    uid3: '',
                    uid4: '',
                    uid5: '',
                    uid6: '',
                },
                idx: -1,        // 记录当前编辑行数，从0开始计
                addFormVisible:false,
                editVisible: false,
            };
        },
        created() {
            this.getData();

            this.$apis.getApprovalProcess().then(res =>{
                this.pData = res.data;            //注意这块因为直接返回了list 所以没有records
            }).catch(err =>{
                this.$message.error(err);
            });
        },
        // 路由的进入和切换回相应的触发activated和deactivated
        activated() {
            //只刷新数据，不改变整体的缓存
            this.getData();
        },
        methods: {
            async getData() {
               this.$apis.getPurchaseRequsetByA(this.query).then(res =>{
                    this.tableData = res.data.records;
                    this.pageTotal = res.data.total || 1;
                    
                    this.tableData.forEach(record => {
                        if(record.rstate == 0)
                        {
                            record.rstate = "已通过";
                        }
                        else if(record.rstate == -1)
                        {
                            record.rstate = "已拒绝";
                        }
                        else
                        {
                            this.pData.forEach(process => {
                                if(process.pid==record.pid){
                                    if(record.rstate == 1)
                                        record.rstate = process.uid1 + "审核中";
                                    else if(record.rstate == 2)
                                        record.rstate = process.uid2 + "审核中";
                                    else if(record.rstate == 3)
                                        record.rstate = process.uid3 + "审核中";
                                    else if(record.rstate == 4)
                                        record.rstate = process.uid4 + "审核中";
                                    else if(record.rstate == 5)
                                        record.rstate = process.uid5 + "审核中";
                                    else if(record.rstate == 6)
                                        record.rstate = process.uid6 + "审核中";
                                }
                                
                            });
                        }
                    });

                }).catch(err =>{
                    this.$message.error(err);
                });
            },
            // 同意操作
            async handleAgree(index, row) {
                this.$apis.approvalDicide({rid:row.prid,dicide:true}).then(res =>{
                    this.$message.success('审批成功');
                }).catch(err =>{
                    this.$message.error(err);
                });
                this.$router.go(0)
            },
            //拒绝
            async handleRefuse(index, row) {
                this.$confirm('确定要拒绝吗？', '提示', {
                    type: 'warning'
                })
                    .then(async () => {
                        this.$apis.approvalDicide({rid:row.prid,dicide:false}).then(res =>{
                            this.$message.success('拒绝成功');
                        }).catch(err =>{
                            this.$message.error(err);
                        });
                    })
                    .catch(() => {});
                    this.$router.go(0)
            }
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
        width: 300px;
        display: inline-block;
    }
    .table {
        width: 100%;
        font-size: 14px;
    }
    .red {
        color: #ff0000;
    }
    .blue {
        color: #00aaff;
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
        width: 325px;
        height: 400px;
        overflow-y:scroll;
        margin: 0 auto;
    }
    .richTextStyle >>> img{
        width: 325px;
    }
    .remarksTitle{
        width: 325px;
        margin: 20px auto;
    }
    .remarksTextStyle{
        width: 325px;
        margin: 0 auto;
    }

    /*由element渲染的dom元素样式，无法在样式表中修改样式，解决方案：写样式的时候添加 >>> */
    .avatar-uploader >>> .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
        width: 362px;
        height: 162px;
    }
    .avatar-uploader >>> .el-upload:hover {
        border-color: #409EFF;
    }
    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 360px;
        height: 160px;
        line-height: 160px;
        text-align: center;
    }
    .avatar {
        width: 360px;
        height: 160px;
        display: block;
    }
</style>
