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
                <el-table-column label="操作" align="center" >
                    <template slot-scope="scope">
                        <p v-if="scope.row.rstate=='已通过'">
                            <el-button
                                    type="text"
                                    @click="handleRequest(scope.$index, scope.row)"
                            >验收申请</el-button>
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

        <!-- 编辑弹出框 -->
        <el-dialog title="新增" :visible.sync="addFormVisible" :close-on-click-modal="false">
            <el-form :model="addForm" :rules="addFormRules" ref="addForm" >
                <el-form-item label="资产名称" prop="aname">
                    <el-input v-model="addForm.aname" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="资产分类" prop="aclass">
                    <select @change="aclassData"
                        v-model="selectData1">
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
                    <select @change="pmData"
                        v-model="selectData2">
                        <option v-for='item in pmData'
                            :key="item.pmid"
                            :value="item">
                            {{item.pmid}}:{{item.pmname}}
                        </option>
                    </select>
                </el-form-item>
                <el-form-item label="采购流程编号" prop="pid">
                    <select @change="pData"
                        v-model="selectData3">
                        <option v-for='item in pData'
                            :key="item.pid"
                            :value="item">
                            {{item.pid}}:{{item.pname}}
                        </option>
                    </select>
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
        <!-- 编辑验收申请弹出框 -->
        <el-dialog title="验收申请" :visible.sync="addFormVisible1" :close-on-click-modal="false">
            <el-form :model="addForm1" :rules="addFormRules" ref="addForm1" >
                <el-form-item label="数量" prop="num">
                    <el-input v-model="addForm1.num" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="价格" prop="price">
                    <el-input v-model="addForm1.price" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="附件" prop="annex">
                    <el-upload
                        class="upload-demo"
                        action = 'http://localhost:8080/accept-request/upload'
                        :before-upload="beforeUpload"
                        :on-preview="handlePreview"
                        :on-remove="handleRemove"
                        
                        multiple
                        :limit="1"
                        :on-exceed="handleExceed"
                        :file-list="fileList">
                        <el-button size="small" type="primary">点击上传</el-button>
                        <div slot="tip" class="el-upload__tip">只能上传一个文件，且不超过2M</div>
                    </el-upload>
                </el-form-item>
                <el-form-item label="供货商" prop="supplier">
                    <el-input v-model="addForm1.supplier" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="评价" prop="evaluate">
                    <el-input v-model="addForm1.evaluate" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="验收流程编号" prop="pid">
                    <select @change="pData1"
                        v-model="selectData4">
                        <option v-for='item in pData1'
                            :key="item.pid"
                            :value="item">
                            {{item.pid}}:{{item.pname}}
                        </option>
                    </select>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
            <el-button @click="addFormVisible1 = false">取消</el-button>
            <el-button type="primary" @click="addSubmit1" :loading="addLoading">提交</el-button>
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
                    uid:'',
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
                addForm1:{
                    rid: "",   //要跟采购申请的一样
                    aname: "",   //跟采购申请一样
                    acid: "",    //跟采购申请一样
                    num: "",      //输入
                    price: "",   //输入
                    annex: "",   //其它地方实际处理  这里给个名字或者路径
                    supplier: "",  //输入
                    evaluate: "",    //输入
                    astate: "",      //自动
                    pid: "",         //选择
                    manufacturer: "", //输入
                },
                fileList: [],
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
                pData1:{       //从这个里面选流程
                    pid: '',
                    pname: '',
                    uid1: '',
                    uid2: '',
                    uid3: '',
                    uid4: '',
                    uid5: '',
                    uid6: '',
                },
                selectData1:{},
                selectData2:{},
                selectData3:{},
                selectData4:{},
                idx: -1,        // 记录当前编辑行数，从0开始计
                addFormVisible:false,
                addFormVisible1:false,
                editVisible: false,
                form: {},
                addFormRules: {
                    aname: [{ required: true, message: '不能为空', trigger: 'blur' }],
                    //aclass: [{ required: true, message: '不能为空', trigger: 'blur' }],
                    num: [{ required: true, message: '不能为空', trigger: 'blur' }],
                    
                    price: [{ required: true, message: '不能为空', trigger: 'blur' }],
                    budget: [{ required: true, message: '不能为空', trigger: 'blur' }],
                    //pmname: [{ required: true, message: '不能为空', trigger: 'blur' }],
                    //pid: [{ required: true, message: '不能为空', trigger: 'blur' }],
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
                this.pData = res.data;            //注意这块因为直接返回了list 所以没有records
            }).catch(err =>{
                this.$message.error(err);
            });
            this.$apis.getAcceptProcess().then(res =>{
                this.pData1 = res.data;            //注意这块因为直接返回了list 所以没有records
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
                this.$apis.getPurchaseRequsetByUid(this.query).then(res =>{
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
            beforeUpload(file) {
                this.addForm1.annex = file.name;
            },
            addSubmit: function() {
                this.$refs.addForm.validate(valid => {
                    if (valid) {
                        this.$confirm("确认提交吗？", "提示", {}).then(() => {
                            this.addLoading = true;

                            this.addForm.acid=this.selectData1.acid;
                            this.addForm.aclass=this.selectData1.acname;
                            this.addForm.pmid=this.selectData2.pmid;
                            this.addForm.pmname=this.selectData2.pmname;

                            this.addForm.pid=this.selectData3.pid;
                            let user = this.$store.state.userInfo.user;
                            this.addForm.uid=user.uid;
                            this.addForm.uname=user.uname;
                            this.$apis.addPurchaseRequest(this.addForm).then(res => {
                                 this.addLoading = false;
                                this.$message({
                                    message: "提交成功",
                                    type: "success"
                                });
                                this.$refs["addForm"].resetFields();
                                this.addFormVisible = false;
                            }).catch(err =>{
                                this.$message.error("请输入正确的数据，或刷新后再试");
                                this.addLoading = false;
                            });
                        });
                    }
                });
            },
            addSubmit1: function() {
                this.$refs.addForm1.validate(valid => {
                    if (valid) {
                        this.$confirm("确认提交吗？", "提示", {}).then(() => {
                            this.addLoading = true;
                            this.addForm1.pid=this.selectData4.pid;
                            this.$apis.addAcceptRequest(this.addForm1).then(res => {
                                 this.addLoading = false;
                                this.$message({
                                    message: "提交成功",
                                    type: "success"
                                });
                                this.$refs["addForm1"].resetFields();
                                this.addFormVisible1 = false;
                                this.$router.go(0)
                            }).catch(err =>{
                                this.$message.error("请输入正确的数据，或刷新后再试,并注意不要重复提交");
                                this.addLoading = false;
                            });
                            
                        });
                    }
                });
            },
            handleAdd: function() {
                this.addFormVisible = true;
            },
            
            resetForm(formName) {
                this.$refs[formName].resetFields();
                this.editVisible = false;
            },
            handleRequest: function(index,row){
                this.addForm1.rid = row.prid;
                this.addForm1.aname = row.aname;
                this.addForm1.acid = row.acid;
                this.addForm1.manufacturer = row.manufacturer
                this.addForm1.astate = 1;
                this.addFormVisible1 = true;

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
