<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-calendar"></i> 采购申请
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>

        <div class="container">
            <div class="handle-box">
                <el-input v-model="query.aname" placeholder="采购物资名称" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
                <el-button type="primary" icon="el-icon-edit"  @click="handleAdd">新增</el-button>
            </div>
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
                
                
                <el-table-column label="操作" align="center" width="220">
                    <template slot-scope="scope">
                        <el-button
                                type="text"
                                @click="handleAgree(scope.$index, scope.row)"
                        >通过</el-button>
                        <el-button
                                type="text"
                                class="red"
                                @click="handleRefuse(scope.$index, scope.row)"
                        >拒绝</el-button>
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

        <!-- 编辑弹出框 -->
        <el-dialog title="新增" :visible.sync="addFormVisible" :close-on-click-modal="false">
            <el-form :model="addForm" :rules="addFormRules" ref="addForm" >
                <el-form-item label="资产名称" prop="aname">
                    <el-input v-model="addForm.aname" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="资产分类" prop="aclass">
                    <select @change="aclassData"
                        v-model="selectAC">
                        <option v-for='item in aclassData'
                            :key="item.acid"
                            :value="item">
                            {{item.acid}}:{{item.acname}}
                        </option>
                    </select>
                </el-form-item>
                <el-form-item label="数量" prop="num">
                    <el-input v-model="addForm.num" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="预算" prop="budget">
                    <el-input v-model="addForm.budget" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="采购方式名称" prop="pmname">
                    <el-input v-model="addForm.pmname" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="采购流程编号" prop="pid">
                    <el-input v-model="addForm.pid" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="详细描述" prop="specification">
                    <el-input v-model="addForm.specification" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="生产厂家" prop="manufacturer">
                    <el-input v-model="addForm.manufacturer" auto-complete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
            <el-button @click="addFormVisible = false">取消</el-button>
            <el-button type="primary" @click="addSubmit" :loading="addLoading">提交</el-button>
            </div>
        </el-dialog>
        
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
                addForm:{
                    aname : "",     //输入
                    aclass: "",     //选
                    acid: "",       
                    num: "",            //输入
                    budget: "",         //输入
                    uid: "",            //自动
                    uname: "",      //自动
                    pmid: "",       
                    pmname: "",     //选
                    rstate: 1,     //自动
                    pid: "",        //选
                    specification: "",  //输入
                    manufacturer: "",   //输入
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
                
                aclassData:{   //从这个里面选分类
                    acid: "",
                    acname: "",
                },
                pmData:{    //从这个里面选采购方式
                    pmid: '',
                    pmname: '',
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
                selectAC:{},
                idx: -1,        // 记录当前编辑行数，从0开始计
                addFormVisible:false,
                editVisible: false,
                form: {},
                rules: {
                    pmname: [{ required: true, message: '请输入名称', trigger: 'blur' }],
                },

            };
        },
        created() {
            this.getData();
            this.$apis.getassetClass({
                acid:'',
                acname: '',
                pageSize: 100
            },).then(res =>{
                this.aclassData = res.data.records;
            }).catch(err =>{
                this.$message.error(err);
            });
  
            this.$apis.getPurchaseMethod({pmid:'',pmname:'',pageSize: 100}).then(res =>{
                this.pmData = res.data.records;
            }).catch(err =>{
                this.$message.error(err);
            });

            this.$apis.getApprovalProcess().then(res =>{
                this.pData = res.data.records;
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
                }).catch(err =>{
                    this.$message.error(err);
                });
            },

            addSubmit: function() {
                this.$refs.addForm.validate(valid => {
                    if (valid) {
                        this.$confirm("确认提交吗？", "提示", {}).then(() => {
                            this.addLoading = true;
                            this.addForm.acid=this.selectAC.acid;
                            this.addForm.aclass=this.selectAC.acid;
                            let param = Object.assign({}, this.addForm);
                            this.$apis.addPurchaseRequest(this.addForm).then(res => {
                                this.addLoading = false;
                                this.$message({
                                    message: "提交成功",
                                    type: "success"
                                });
                                this.$refs["addForm"].resetFields();
                                this.addFormVisible = false;
                                this.getResult(1);
                            });
                        });
                    }
                });
            },
           
            handleSearch() {
                this.$set(this.query, 'pageIndex', 1);
                this.getData();
                this.query.pmname=""

            },
            handleAdd: function() {
                this.addFormVisible = true;
                
            },
            // 保存修改或添加
            async saveEdit(formName) {
                this.$refs[formName].validate(async (valid) => {
                    if (valid) {
                        this.$apis.savePurchaseMethod(this.form).then(res =>{
                            this.$message.success('修改成功！');
                            this.$set(this.tableData, this.idx, res.data);
                        }).catch(err =>{
                            this.$message.error(err);
                        });

                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                    this.editVisible = false;
                });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
                this.editVisible = false;
            },
            // 同意操作
            async handleAgree(index, row) {

            },
            //拒绝
            async handleRefuse(index, row) {
                this.$confirm('确定要删除吗？', '提示', {
                    type: 'warning'
                })
                    .then(async () => {
                        this.$apis.deletePurchaseMethod({pmid:row.pmid}).then(res =>{
                            this.$message.success('删除成功');
                            this.tableData.splice(index, 1);
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
