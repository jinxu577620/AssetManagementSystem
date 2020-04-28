<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-calendar"></i> 用户管理
                </el-breadcrumb-item>
                <el-breadcrumb-item>用户列表</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-input v-model="query.name" placeholder="用户名" class="handle-input mr10"></el-input>
                <el-input v-model="query.tel" placeholder="手机号" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
            </div>
            <el-table
                :data="tableData"
                border
                stripe
                class="table"
                ref="multipleTable"
                header-cell-class-name="table-header"
            >
                <el-table-column type="index" width="50" align="center"></el-table-column>
                <el-table-column prop="name" label="名称" align="center" width="100"></el-table-column>
                <el-table-column label="头像" align="center" width="100">
                    <template slot-scope="scope">
                        <el-image
                                class="table-td-face"
                                :src="scope.row.headImgUrl"
                                :preview-src-list="[scope.row.headImgUrl]"
                                style="border-radius: 50%"
                        ></el-image>
                    </template>
                </el-table-column>
                <el-table-column prop="tel" label="手机" align="center" width="120"></el-table-column>
                <el-table-column label="角色" align="center" width="100">
                    <template slot-scope="scope">
                        <span v-if="scope.row.role===0">
                            <el-tag type="success">管理员</el-tag>
                        </span>
                        <span v-else-if="scope.row.role===1">
                            <el-tag type="warning">财务</el-tag>
                        </span>
                        <span v-else-if="scope.row.role===2">
                            <el-tag type="info">销售员</el-tag>
                        </span>
                        <span v-else-if="scope.row.role===3">
                            <el-tag>发货员</el-tag>
                        </span>
                        <span v-else>无</span>
                    </template>
                </el-table-column>
                <el-table-column label="旺旺号" align="center">
                    <template slot-scope="scope">
                        <span v-if="scope.row.role===2">
                            <span>{{scope.row.wangwang}}</span>
                        </span>
                        <span v-else>无</span>
                    </template>
                </el-table-column>
                <el-table-column label="邮箱">
                    <template slot-scope="scope">
                        <span v-if="scope.row.email">
                            <span>{{scope.row.email}}</span>
                        </span>
                        <span v-else>无</span>
                    </template>
                </el-table-column>
                <el-table-column label="状态" align="center" width="100">
                    <template slot-scope="scope">
                        <span v-if="scope.row.status==='正常'">
                            <el-tag type="success">正常</el-tag>
                        </span>
                        <span v-else-if="scope.row.status==='冻结'">
                            <el-tag type="danger">冻结</el-tag>
                        </span>
                        <span v-else-if="scope.row.status==='注销'">
                            <el-tag type="warning">注销</el-tag>
                        </span>
                        <span v-else>无</span>
                    </template>
                </el-table-column>
                <el-table-column label="操作" align="center" width="220">
                    <template slot-scope="scope">
                        <el-button
                            type="text"
                            icon="el-icon-edit"
                            @click="handleEdit(scope.$index, scope.row)"
                        >编辑</el-button>
                        <el-button
                                v-if="scope.row.tel !== '15159123141' && scope.row.status==='正常'"
                                type="text"
                                icon="el-icon-lock"
                                class="blue"
                                @click="handleFreeze(scope.$index, scope.row)"
                        >冻结</el-button>
                        <el-button
                                v-if="scope.row.tel !== '15159123141' && scope.row.status==='冻结'"
                                type="text"
                                icon="el-icon-lock"
                                class="blue"
                                @click="handleUnFreeze(scope.$index, scope.row)"
                        >解冻</el-button>
                        <el-button
                                v-if="scope.row.tel !== '15159123141'"
                                type="text"
                                icon="el-icon-delete"
                                class="red"
                                @click="handleDelete(scope.$index, scope.row)"
                        >删除</el-button>
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
        <el-dialog title="编辑" :visible.sync="editVisible" width="780px">
            <el-form ref="form" :model="form" :rules="rules" label-width="100px">
                <el-form-item label="用户头像">
                    <my-vue-upload-cropper
                            ref="headImg"
                            :currentImg="form.headImgUrl"
                            :imgWidth="120"
                            :imgHeight="120"
                            :uploadPath="'user/'"
                    ></my-vue-upload-cropper>
                </el-form-item>
                <el-form-item label="名称" prop="name">
                    <el-input v-model="form.name"></el-input>
                </el-form-item>
                <el-form-item label="手机" prop="tel">
                    <el-input v-model="form.tel"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input v-model="form.password" show-password></el-input>
                </el-form-item>
                <el-form-item label="邮箱" prop="email">
                    <el-input v-model="form.email"></el-input>
                </el-form-item>
                <el-form-item label="角色">
                    <el-radio-group v-model="form.role">
                        <el-radio :label="0">管理员</el-radio>
                        <el-radio :label="1">财务</el-radio>
                        <el-radio :label="2">销售员</el-radio>
                        <el-radio :label="3">发货员</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item v-if="form.role===2" label="销售旺旺" prop="wangwang">
                    <el-input v-model="form.wangwang" placeholder="多个用逗号分隔"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="resetForm('form')">取 消</el-button>
                <el-button type="primary" @click="saveEdit('form')">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    import MyVueUploadCropper from '../../components/utils/MyVueUploadCropper'
export default {
    name: 'UsersList',
    components: {
        MyVueUploadCropper
    },
    data() {
        return {
            query: {
                name: '',
                tel: '',
                pageIndex: 1,
                pageSize: 15
            },
            pageTotal: 0,
            tableData: [],
            idx: -1,        // 记录当前编辑行数，从0开始计

            editVisible: false,
            form: {},
            rules: {
                name: [{ required: true, message: '请输入名称', trigger: 'blur' }],
                password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
                tel: [{ required: true, message: '请输入手机', trigger: 'blur' }],
                weight: [
                    { required: true, message: '请输入权重', trigger: 'blur' },
                    { type: 'number', message: '权重必须为数字值', trigger: 'blur'}
                ],
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
            this.$apis.getUsers(this.query).then(res =>{
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
        // 编辑某个Banner
        handleEdit(index, row) {
            this.idx = index;
            this.form = row;
            this.editVisible = true;
        },

        // 保存修改或添加
        async saveEdit(formName) {
            this.$refs[formName].validate(async (valid) => {
                if (valid) {
                    // 保存修改或添加

                    let headImg = await this.$refs.headImg.uploadImage();
                    if (!headImg) {
                        this.$message.error('请先上传活动');
                        return;
                    }else{
                        this.$set(this.form, "headImgUrl", headImg);
                    }

                    if (this.form.password.length >= 6 && this.form.password.length !== 32) {
                        this.$set(this.form, "password", this.$md5(this.form.password));
                    }

                    this.$apis.saveUser(this.form).then(res =>{
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

        // 冻结
        handleFreeze(index, row){
            // 二次确认冻结
            this.$confirm('确定要冻结该用户吗？', '提示', {
                type: 'warning'
            }).then( () => {
                this.$apis.freezeUser({id:row.id}).then(res =>{
                    this.$message.success('冻结成功！');
                    this.$set(this.tableData, index, res.data);
                }).catch(err =>{
                    this.$message.error(err);
                });
            }).catch(() => {});

        },

        // 冻结
        handleUnFreeze(index, row){
            // 二次确认解冻
            this.$confirm('确定要解冻该用户吗？', '提示', {
                type: 'warning'
            }).then( () => {
                this.$apis.unFreezeUser({id:row.id}).then(res =>{
                    this.$message.success('解冻成功！');
                    this.$set(this.tableData, index, res.data);
                }).catch(err =>{
                    this.$message.error(err);
                });
            }).catch(() => {});
        },

        // 删除操作
        handleDelete(index, row) {
            // 二次确认删除
            this.$confirm('确定要删除吗？', '提示', {
                type: 'warning'
            })
            .then(async () => {
                this.$apis.deleteUser({id:row.id}).then(res =>{
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
