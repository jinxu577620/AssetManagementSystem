<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-calendar"></i> 验收流程
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
                <el-table-column prop="pid" label="验收流程编号" width="80" align="center" ></el-table-column>
                <el-table-column prop="pname" label="验收流程名称" align="center" ></el-table-column>
                <el-table-column prop="uid1" label="第一验收人工号" align="center" ></el-table-column>
                <el-table-column prop="uid2" label="第二验收人工号" align="center" ></el-table-column>
                <el-table-column prop="uid3" label="第三验收人工号" align="center" ></el-table-column>
                <el-table-column prop="uid4" label="第四验收人工号" align="center" ></el-table-column>
                <el-table-column prop="uid5" label="第五验收人工号" align="center" ></el-table-column>
                <el-table-column prop="uid6" label="第六验收人工号" align="center" ></el-table-column>
            </el-table>
            
        </div>
        <!-- 编辑弹出框 -->
        <el-dialog title="新增" :visible.sync="addFormVisible" :close-on-click-modal="false">
        <el-form :model="addForm" :rules="addFormRules" ref="addForm" >
            <el-form-item label="验收流程名称" prop="pname">
                <el-input v-model="addForm.pname" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="第一验收人" prop="uid1">
                <el-input v-model="addForm.uid1" auto-complete="off" ></el-input>
            </el-form-item>
            <el-form-item label="第二验收人" prop="uid2">
                <el-input v-model="addForm.uid2" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="第三验收人" prop="uid3">
                <el-input v-model="addForm.uid3" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="第四验收人" prop="uid4">
                <el-input v-model="addForm.uid4" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="第五验收人" prop="uid5">
                <el-input v-model="addForm.uid5" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="第六验收人" prop="uid6">
                <el-input v-model="addForm.uid6" auto-complete="off"></el-input>
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
        name: 'acceptProcess',
        data() {
            return {
                query: {
                },
                curentroleid: "",
                //添加按钮Loading加载
                addLoading: false,
                pageTotal: 0,
                tableData: [ {
                    pid: '',
                    pname: '',
                    uid1: '',
                    uid2: '',
                    uid3: '',
                    uid4: '',
                    uid5: '',
                    uid6: '',
                } ],
                idx: -1,        // 记录当前编辑行数，从0开始计
                addFormVisible:false,
                editVisible: false,
                form: {
                },
                //新增界面数据
                addForm: {
                    pname: '',
                    uid1: '',
                    uid2: '',
                    uid3: '',
                    uid4: '',
                    uid5: '',
                    uid6: '',
                },
                addFormRules: {
                    pname: [{ required: true, message: '不能为空', trigger: 'blur' }],
                    uid1: [{ required: true, message: '不能为空', trigger: 'blur' }],
                },
            };
        },
        created() {
            this.getData();
        },
        // 路由的进入和切换回相应的触发activated和deactivated
        activated() {
            //只刷新数据，不改变整体的缓存
            this.getData();
        },
        
        methods: {
            async getData() {
                this.$apis.getAcceptProcess(this.query).then(res =>{
                    this.tableData = res.data;
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
                            //let param = Object.assign({}, this.addForm);
                            this.$apis.addAcceptProcess(this.addForm).then(res => {
                                this.addLoading = false;
                                this.$message({
                                    message: "提交成功",
                                    type: "success"
                                });
                                this.addFormVisible = false;
                            }).catch(err =>{
                                this.$message.error("不存在此用户或用户权限不足，请输入正确的工号，刷新后再试");
                                this.addLoading = false;
                            });
                        });
                    }

                });
            },
            //显示新增界面
            handleAdd: function() {
                this.addFormVisible = true;
                this.addForm = {
                    pname: '',
                    uid1: '',
                    uid2: '',
                    uid3: '',
                    uid4: '',
                    uid5: '',
                    uid6: '',
                };
            },
            // 触发搜索按钮
            // 分页导航
           
            resetForm(formName) {
                this.$refs[formName].resetFields();
                this.editVisible = false;
            },
            
        }
    };
</script>
