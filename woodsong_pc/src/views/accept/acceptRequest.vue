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
                <el-table-column prop="rid" label="申请编号" width="80" align="center" ></el-table-column>
                <el-table-column prop="aname" label="资产名称" align="center" ></el-table-column>
                <el-table-column prop="acid" label="资产分类编号" align="center" ></el-table-column>
                <el-table-column prop="num" label="数量" align="center" ></el-table-column>
                <el-table-column prop="price" label="价格" align="center" ></el-table-column>
                <el-table-column prop="annex" label="附件" align="center" ></el-table-column>
                <el-table-column prop="supplier" label="供应商" align="center" ></el-table-column>
                <el-table-column prop="evaluate" label="评价" align="center" ></el-table-column>
                
                <el-table-column prop="astate" label="状态" align="center" ></el-table-column>
                <el-table-column prop="pid" label="审批流程编号" align="center" ></el-table-column>
                <el-table-column prop="manufacturer" label="生产厂家" align="center" ></el-table-column>
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
        name: 'accept',
        data() {
            return {
                query: {
                    pageIndex: 1,
                    pageSize: 15
                },
                pageTotal: 0,
                tableData: {
                    
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

            this.$apis.getAcceptProcess().then(res =>{
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
                let user = this.$store.state.userInfo.user;
                this.query.uid = user.uid;
                this.$apis.getAcceptRequsetByUid(this.query).then(res =>{
                    this.tableData = res.data;
                    this.pageTotal = res.data.total || 1;
                    
                    this.tableData.forEach(record => {
                        if(record.astate == 0)
                        {
                            record.astate = "已通过";
                        }
                        else if(record.astate == -1)
                        {
                            record.astate = "已拒绝";
                        }
                        else
                        {
                            this.pData.forEach(process => {
                                if(process.pid==record.pid){
                                    if(record.astate == 1)
                                        record.astate = process.uid1 + "审核中";
                                    else if(record.astate == 2)
                                        record.astate = process.uid2 + "审核中";
                                    else if(record.astate == 3)
                                        record.astate = process.uid3 + "审核中";
                                    else if(record.astate == 4)
                                        record.astate = process.uid4 + "审核中";
                                    else if(record.astate == 5)
                                        record.astate = process.uid5 + "审核中";
                                    else if(record.astate == 6)
                                        record.astate = process.uid6 + "审核中";
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
                this.$apis.AcceptDicide({rid:row.rid,dicide:true}).then(res =>{
                    this.$message.success('审批成功');
                    this.$router.go(0)
                }).catch(err =>{
                    this.$message.error(err);
                });
            },
            //拒绝
            async handleRefuse(index, row) {
                this.$confirm('确定要拒绝吗？', '提示', {
                    type: 'warning'
                })
                    .then(async () => {
                        this.$apis.AcceptDicide({rid:row.rid,dicide:false}).then(res =>{
                            this.$message.success('拒绝成功');
                            this.$router.go(0)
                        }).catch(err =>{
                            this.$message.error(err);
                        });
                    })
                    .catch(() => {});
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
