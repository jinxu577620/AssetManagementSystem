<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-calendar"></i> 用户管理
                </el-breadcrumb-item>
                <el-breadcrumb-item>我的客户</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-input v-model="query.wangwang" placeholder="客户旺旺号" class="handle-input mr10"></el-input>
                <el-date-picker
                        v-model="query.createDate"
                        type="date"
                        placeholder="登记日期"
                        align="right"
                        class="handle-input mr10"
                        :picker-options="pickerOptions">
                </el-date-picker>
                <el-input v-model="query.name" placeholder="客户名称" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
                <el-button type="primary" icon="el-icon-download" class="handle-del" @click="exportExcel">导出</el-button>
            </div>
            <el-table
                :data="tableData"
                border
                class="table"
                ref="multipleTable"
                :row-class-name="tableRowClassName"
            >
                <!--<el-table-column prop="id" label="客户id" align="center"></el-table-column>-->
                <el-table-column label="详情" type="expand" align="center" width="50" fixed>
                    <template slot-scope="props">
                        <el-form label-position="left" inline class="demo-table-expand">
                            <el-form-item label="客户Id">
                                <span v-if="props.row.id">{{ props.row.id }}</span>
                                <span v-else>无</span>
                            </el-form-item>
                            <el-form-item label="保护期限">
                                <span v-if="props.row.overdueTime">{{ props.row.overdueTime }}</span>
                                <span v-else>无</span>
                            </el-form-item>
                            <el-form-item label="客户名">
                                <span v-if="props.row.name">{{ props.row.name }}</span>
                                <span v-else>无</span>
                            </el-form-item>
                            <el-form-item label="手机">
                                <span v-if="props.row.tel">{{ props.row.tel }}</span>
                                <span v-else>无</span>
                            </el-form-item>
                            <el-form-item label="微信">
                                <span v-if="props.row.weixin">{{ props.row.weixin }}</span>
                                <span v-else>无</span>
                            </el-form-item>
                            <el-form-item label="客户类型">
                                <span v-if="props.row.type==='普通客户'">
                                    <el-tag type="success">普通客户</el-tag>
                                </span>
                                <span v-else-if="props.row.type==='刷单'">
                                    <el-tag type="warning">刷单</el-tag>
                                </span>
                                <span v-else-if="props.row.type==='已购客户'">
                                    <el-tag type="info">已购客户</el-tag>
                                </span>
                                <span v-else>无</span>
                            </el-form-item>
                            <el-form-item label="客户需求">
                                <span v-if="props.row.requirement">{{ props.row.requirement }}</span>
                                <span v-else>无</span>
                            </el-form-item>
                        </el-form>
                    </template>
                </el-table-column>
                <el-table-column prop="wangwang" label="旺旺号" align="center"></el-table-column>
                <el-table-column label="客户名" align="center">
                    <template slot-scope="scope">
                        <span v-if="scope.row.name">{{ scope.row.name }}</span>
                        <span v-else>无</span>
                    </template>
                </el-table-column>
                <el-table-column label="是否添加微信" align="center">
                    <template slot-scope="scope">
                        <span v-if="scope.row.addWeiXin===0">
                            <el-tag type="danger">未添加</el-tag>
                        </span>
                        <span v-else-if="scope.row.addWeiXin===1">
                            <el-tag type="success">已添加</el-tag>
                        </span>
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
                <el-table-column label="是否购买" align="center">
                    <template slot-scope="scope">
                        <span v-if="scope.row.purchased===0">
                            <el-tag type="info">未购买</el-tag>
                        </span>
                        <span v-else-if="scope.row.purchased===1">
                            <el-tag type="success">已购买</el-tag>
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
                <el-table-column label="保护时效" align="center">
                    <template slot-scope="scope">
                        <el-tag v-if="remindStyle(scope.row.overdueTime)" type="success">{{remainDays(scope.row.overdueTime)}}</el-tag>
                        <el-tag v-else type="danger">{{remainDays(scope.row.overdueTime)}}</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="操作" align="center" width="220" v-if="user.role < 3">
                    <template slot-scope="scope">
                        <el-button
                            type="text"
                            icon="el-icon-edit"
                            @click="handleEdit(scope.$index, scope.row)"
                        >编辑</el-button>
                        <el-button
                                type="text"
                                icon="el-icon-bell"
                                class="red"
                                @click="handleDelay(scope.$index, scope.row)"
                        >延期</el-button>
                        <el-button
                                type="text"
                                icon="el-icon-document-checked"
                                class="green"
                                @click="createOrder(scope.$index, scope.row)"
                        >下单</el-button>
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

        <!--导出的订单信息-->
        <el-table id="exportTable" :data="exportTableData" style="display: none;">
            <el-table-column prop="id" label="客户Id"></el-table-column>
            <el-table-column prop="wangwang" label="旺旺号"></el-table-column>
            <el-table-column prop="name" label="客户名"></el-table-column>
            <el-table-column prop="tel" label="手机"></el-table-column>
            <el-table-column prop="weixin" label="微信"></el-table-column>
            <el-table-column label="是否添加微信">
                <template slot-scope="scope">
                    <span v-if="scope.row.addWeiXin===0">
                        <el-tag type="info">未添加</el-tag>
                    </span>
                    <span v-else-if="scope.row.addWeiXin===1">
                        <el-tag type="success">已添加</el-tag>
                    </span>
                    <span v-else>无</span>
                </template>
            </el-table-column>
            <el-table-column prop="intention" label="客户意向"></el-table-column>
            <el-table-column prop="type" label="客户类型"></el-table-column>
            <el-table-column label="是否购买">
                <template slot-scope="scope">
                        <span v-if="scope.row.purchased===0">
                            <el-tag type="info">未购买</el-tag>
                        </span>
                    <span v-else-if="scope.row.purchased===1">
                            <el-tag type="success">已购买</el-tag>
                        </span>
                    <span v-else>无</span>
                </template>
            </el-table-column>
            <el-table-column prop="origin" label="来源平台"></el-table-column>
            <el-table-column prop="overdueTime" label="保护时效"></el-table-column>
            <el-table-column prop="requirement" label="客户需求"></el-table-column>
        </el-table>

        <!-- 编辑弹出框 -->
        <el-dialog title="编辑" :visible.sync="editVisible" width="780px">
            <el-form ref="form" :model="form" :rules="rules" label-width="100px">
                <el-form-item label="旺旺号" prop="wangwang">
                    <el-input v-model="form.wangwang"></el-input>
                </el-form-item>
                <el-form-item label="客户名称" prop="name">
                    <el-input v-model="form.name"></el-input>
                </el-form-item>
                <el-form-item label="手机" prop="tel">
                    <el-input v-model="form.tel"></el-input>
                </el-form-item>
                <el-form-item label="微信号" prop="weixin">
                    <el-input v-model="form.weixin"></el-input>
                </el-form-item>
                <el-form-item label="是否加了微信">
                    <el-radio-group v-model="form.addWeiXin">
                        <el-radio :label="0">未添加</el-radio>
                        <el-radio :label="1">已添加</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="客户意向">
                    <el-rate
                            v-model="form.intention"
                            :colors="colors"
                            style="margin-top: 10px"
                    >
                    </el-rate>
                </el-form-item>
                <el-form-item label="客户类型">
                    <el-radio-group v-model="form.type">
                        <el-radio label="普通客户">普通客户</el-radio>
                        <el-radio label="刷单">刷单</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="是否购买">
                    <el-radio-group v-model="form.purchased">
                        <el-radio :label="0">未购买</el-radio>
                        <el-radio :label="1">已购买</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="来源平台">
                    <el-radio-group v-model="form.origin">
                        <el-radio label="天猫">天猫</el-radio>
                        <el-radio label="京东">京东</el-radio>
                        <el-radio label="东方红">东方红</el-radio>
                        <el-radio label="木佰年">木佰年</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="客户需求">
                    <el-input type="textarea" v-model="form.requirement"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="resetForm('form')">取 消</el-button>
                <el-button type="primary" @click="submitForm('form')">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    import moment from 'moment'   // 日期处理类库
    // 导出需要安装xlsx 与 file-saver依赖: npm install --save xlsx file-saver
    import FileSaver from 'file-saver'
    import XLSX from 'xlsx'
export default {
    name: 'MyCustomers',
    data() {
        return {
            query: {
                name: '',
                createDate: '',
                wangwang: '',
                pageIndex: 1,
                pageSize: 15
            },

            pageTotal: 0,
            tableData: [],
            exportTableData: [],  // 导出的订单表单数据
            idx: -1,        // 记录当前编辑行数，从0开始计

            colors: ['#99A9BF', '#F7BA2A', '#FF9900'],
            editVisible: false,
            form: { },

            pickerOptions: {
                shortcuts: [{
                    text: '今天',
                    onClick(picker) {
                        picker.$emit('pick', new Date());
                    }
                }, {
                    text: '明天',
                    onClick(picker) {
                        const date = new Date();
                        date.setTime(date.getTime() + 3600 * 1000 * 24);
                        picker.$emit('pick', date);
                    }
                }, {
                    text: '两天后',
                    onClick(picker) {
                        const date = new Date();
                        date.setTime(date.getTime() + 3600 * 1000 * 24 * 2);
                        picker.$emit('pick', date);
                    }
                }, {
                    text: '三天后',
                    onClick(picker) {
                        const date = new Date();
                        date.setTime(date.getTime() + 3600 * 1000 * 24 * 3);
                        picker.$emit('pick', date);
                    }
                }, {
                    text: '一周后',
                    onClick(picker) {
                        const date = new Date();
                        date.setTime(date.getTime() + 3600 * 1000 * 24 * 7);
                        picker.$emit('pick', date);
                    }
                }]
            },
            rules: {
                wangwang: [{ required: true, message: '请输入旺旺号', trigger: 'blur' }]
            },
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
            if (this.query.createDate !== undefined && this.query.createDate != null && this.query.createDate !== "") {
                // 格式化时间格式
                let date = moment(this.query.createDate).format('YYYY-MM-DD');
                this.$set(this.query, "createDate", date);
            }
            this.$apis.getMyCustomers(this.query).then(res =>{
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
        // 根据过期日期，区分表格状态
        tableRowClassName({row, rowIndex}) {
            if (moment(row.overdueTime).subtract(1, 'days').isBefore(moment())) {
                return 'warning-row';
            }
        },
        // 保护剩余天数样式提示
        remindStyle(val) {
            if (moment(val).diff(moment(), 'day') > 0) return true;
            return false;
        },
        // 计算剩余天数
        remainDays(val) {
            let days = moment(val).diff(moment(), 'day');
            if (days > 365) return "永久";
            else if (days > 0) return days+1+' 天';
            else return moment(val).diff(moment(), 'hour')+' 时';
        },
        // 编辑
        handleEdit(index, row) {
            this.idx = index;
            this.form = row;
            this.editVisible = true;
        },

        async exportExcel() {
            if (this.query.createDate !== undefined && this.query.createDate != null && this.query.createDate !== "") {
                // 格式化时间格式
                let date = moment(this.query.createDate).format('YYYY-MM-DD');
                this.$set(this.query, "createDate", date);
            }
            // 加载要导出的数据，如果用显示表格中数据，分页不完全，所以用另一个表格加载
            this.$apis.getExportCustomers(this.query).then(res =>{
                this.exportTableData = res.data.records;
                let loading = this.$loading({
                    lock: true,
                    text: 'Loading',
                    spinner: 'el-icon-loading',
                    background: 'rgba(0, 0, 0, 0.7)'
                });
                setTimeout(()=>{
                    // 从表格中生成workbook对象
                    let wb = XLSX.utils.table_to_book(document.querySelector('#exportTable'))
                    // get binary string as output
                    let wbout = XLSX.write(wb, {bookType: 'xlsx', bookSST: true, type: 'array'})
                    try {
                        FileSaver.saveAs(new Blob([wbout], {type: 'application/octet-stream'}), '我的客户.xlsx')
                    } catch (e) {
                        if (typeof console !== 'undefined') console.log(e, wbout)
                    }
                    loading.close();
                    return wbout
                },1500);
            }).catch(err =>{
                this.$message.error(err);
            });
        },

        submitForm(formName) {
            this.$refs[formName].validate(async (valid) => {
                if (valid) {

                    this.$apis.saveCustomer(this.form).then(res =>{
                        this.$message.success(`修改第 ${this.idx + 1} 行成功`);
                        this.$set(this.tableData, this.idx, res.data);
                        this.editVisible = false;
                    }).catch(err =>{
                        this.$message.error(err);
                    });
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
        resetForm(formName) {
            // 如果执行下面这句，会编辑一行再编辑另一行会覆盖另一行的信息
            // this.$refs[formName].resetFields();
            this.editVisible = false;
        },

        // 延期操作
        handleDelay(index, row) {
            // 二次确认延期
            this.$confirm('确定要延期继续跟单吗？', '提示', {
                type: 'warning'
            })
            .then(async () => {
                this.$apis.delayCustomer({id:row.id}).then(res =>{
                    this.$message.success('延期成功');
                    this.$set(this.tableData, index, res.data);
                }).catch(err =>{
                    this.$message.error(err);
                });
            })
            .catch(() => {});
        },

        createOrder(index, row) {
            this.$router.push({
                path: "/createOrders",
                query: {
                    customerId: row.id
                }
            })
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
    width: 290px;
    display: inline-block;
}
.table {
    width: 100%;
    font-size: 14px;
}
.red {
    color: #ff0000;
}
.green {
    color: #67C23A;
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
