<template>
    <div class="sidebar">
        <el-menu
            class="sidebar-el-menu"
            :default-active="onRoutes"
            :collapse="collapse"
            background-color="#324157"
            text-color="#bfcbd9"
            active-text-color="#20a0ff"
            unique-opened
            router
        >
            <template v-for="item in items">
                <template v-if="item.subs">
                    <el-submenu v-if="item.role===undefined || (item.role>=user.role)" :index="item.index" :key="item.index">
                        <template slot="title">
                            <i :class="item.icon"></i>
                            <span slot="title">{{ item.title }}</span>
                        </template>
                        <template v-for="subItem in item.subs">
                            <template v-if="subItem.role===undefined || (subItem.role===user.role)">
                                <el-submenu
                                        v-if="subItem.subs"
                                        :index="subItem.index"
                                        :key="subItem.index"
                                >
                                    <template slot="title">{{ subItem.title }}</template>
                                    <el-menu-item
                                            v-for="(threeItem,i) in subItem.subs"
                                            :key="i"
                                            :index="threeItem.index"
                                    >{{ threeItem.title }}</el-menu-item>
                                </el-submenu>
                                <el-menu-item
                                        v-else
                                        :index="subItem.index"
                                        :key="subItem.index"
                                >{{ subItem.title }}</el-menu-item>
                            </template>
                        </template>
                    </el-submenu>
                </template>
                <template v-else>
                    <el-menu-item v-if="item.role===undefined || (item.role===user.role)" :index="item.index" :key="item.index">
                        <i :class="item.icon"></i>
                        <span slot="title">{{ item.title }}</span>
                    </el-menu-item>
                </template>
            </template>
        </el-menu>
    </div>
</template>

<script>
import bus from '../../utils/bus';
export default {
    data() {
        return {
            collapse: false,
            items: [
                {
                    icon: 'el-icon-lx-home',
                    index: '/dashboard',
                    title: '系统首页'
                },
                {
                    icon: 'el-icon-user',
                    index: '/UserList',
                    title: '用户管理'
                },
                {
                    icon: 'el-icon-lx-friend',
                    index: '/customer',
                    title: '系统管理',
                    //role: 2,
                    subs: [
                        {
                            index: '/SysRole',
                            title: '定义采购方式'
                        },
                        {
                            index: '/assetclasss',
                            title: '定义资产分类'
                        },
                        {
                            index: '/approvalProcess',
                            title: '定义审批流程'
                        },
                        {
                            index: '/acceptProcess',
                            title: '定义验收流程'
                        }
                    ]
                },
                {
                    icon: 'el-icon-lx-sort',
                    index: '/createOrders',
                    title: '资产采购',
                    subs: [
                        {
                            index: '/purchaseRequest',
                            title: '采购申请'
                        },
                        {
                            index: '/pendingDeleteOrders',
                            title: '验收申请'
                        },
                        {
                            index: '/deletedOrders',
                            title: '查看审批状态'
                        },
                        {
                            index: '/deletedOrders',
                            title: '查看验收状态'
                        }
                    ]
                },
                {
                    icon: 'el-icon-lx-calendar',
                    index: '/pendingSendOrders',
                    title: '资产管理',
                    subs: [
                        {
                            index: '/getAssets',
                            title: '查看资产'
                        },
                        {
                            index: '/pendingDeliverOrders',
                            title: '资产验收'
                        },
                        {
                            index: '/approvalRequest',
                            title: '资产出库审批'
                        },
                        {
                            index: '/shippedOrders',
                            title: '查看分析数据'
                        },
                        {
                            index: '/shippedOrders',
                            title: '资产报废预警'
                        },
                        {
                            index: '/shippedOrders',
                            title: '资产报废'
                        }
                    ]
                },
                {
                    icon: 'el-icon-lx-calendar',
                    index: '/pendingSendOrders0',
                    title: '资产使用申请',
                    subs: [
                        {
                            index: '/getAssets',
                            title: '资产出库申请'
                        },
                        {
                            index: '/approvalState',
                            title: '查看审批状态'
                        }
                    ]
                }
            ]
        };
    },
    computed: {
        user() {
            let user = this.$store.state.userInfo.user;
            return user ? user : null;
        },
        onRoutes() {
            return this.$route.path.replace('/', '');
        }
    },
    created() {
        // 通过 Event Bus 进行组件间通信，来折叠侧边栏
        bus.$on('collapse', msg => {
            this.collapse = msg;
            bus.$emit('collapse-content', msg);
        });
    }
};
</script>

<style scoped>
.sidebar {
    display: block;
    position: absolute;
    left: 0;
    top: 70px;
    bottom: 0;
    overflow-y: scroll;
}
.sidebar::-webkit-scrollbar {
    width: 0;
}
.sidebar-el-menu:not(.el-menu--collapse) {
    width: 250px;
}
.sidebar > ul {
    height: 100%;
}
</style>
