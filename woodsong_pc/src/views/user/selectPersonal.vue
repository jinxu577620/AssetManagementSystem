<template>
    <el-table
            :data="tableData"
            stripe
            style="width: 100%">
        <el-table-column
                prop="uid"
                label="工号"
                width="120"
                >
            {{user!=="" ? user.uid : ""}}
        </el-table-column>
        <el-table-column
                label="姓名"
                width="120">
            {{user.uname}}
        </el-table-column>
        <el-table-column
                prop="address"
                label="角色名称"
                width="120">
            {{user.cname}}
        </el-table-column>

    <el-table-column
            prop="date"
            label="所属部门"
            width="120">
        {{user.department}}
    </el-table-column>
    <el-table-column
            prop="date"
            label="手机号"
            width="120">
        {{user.tel}}
    </el-table-column>
    <el-table-column
            prop="date"
            label="邮箱"
            width="180">
        {{user.email}}
    </el-table-column>
    <el-table-column
            prop="date"
            label="系统管理权限"
            width="120">
        {{user.smauthority}}
    </el-table-column>
        <el-table-column
                prop="date"
                label="用户管理权限"
                width="120">
            {{user.umauthority}}
        </el-table-column>
        <el-table-column
                prop="date"
                label="资产管理权限"
                width="120">
            {{user.amauthority}}
        </el-table-column>
        <el-table-column
                prop="date"
                label="资产采购权限"
                width="120">
            {{user.pauthority}}
        </el-table-column>
        <el-table-column
                prop="date"
                label="资产审批权限"
                width="120">
            {{user.apauthority}}
        </el-table-column>
        <el-table-column
                prop="date"
                label="账号状态"
                width="120">
            {{user.ustate}}
        </el-table-column>
    </el-table>

</template>

<script>
    export default {
        data() {
            return {
                tableData: [ {
                    uid: '',
                    uname: '',
                    cname: '',
                    department:'',
                    tel:'',
                    ustate:'',
                    email:'',
                    smauthority:'',
                    umauthority:'',
                    amauthority :'',
                    pauthority:'',
                    apauthority:''
                } ]
            }
        },
        name: "selectPersonal",
        computed: {
            user() {
                let user = this.$store.state.userInfo.user;
                return user ? user : null;
            }
        }
    }
</script>