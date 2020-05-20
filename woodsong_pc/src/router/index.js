import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        redirect: '/dashboard'
    },
    
    {
        path: '/',
        component: () => import('../components/common/Home.vue'),
        meta: { title: '框架页面' },
        children: [
            {
                path: '/selectPersonal',
                name: 'selectPersonal',
                component :() => import('../views/user/selectPersonal.vue'),
                meta: { title: '查看个人信息' }
            },
            {
                path: '/updatePassword',
                name: 'updatePassword',
                component :() => import('../views/user/updatePassword.vue'),
                meta: { title: '修改密码' }
            },
            {
                path: '/dashboard',
                component: () => import('../views/Dashboard.vue'),
                meta: { title: '系统首页' }
            },
            {
                path: '/SysRole',
                component: () => import('../views/purchaseMethod/SysRole.vue'),
                meta: { title: '定义采购方式', permission: true }
            },
            {
                path: '/assetclasss',
                component: () => import('../views/asset/assetclasss.vue'),
                meta: { title: '定义资产分类', permission: true }
            },
            {
                path: '/userList',
                component: () => import('../views/user/UserList.vue'),
                meta: { title: '用户列表', permission: true}
            },
            {
                path: '/purchaseRequest',
                component: () => import('../views/purchase/purchaseRequest.vue'),
                meta: { title: '采购申请'}
            },
            {
                path: '/approvalPurchaseRequest',
                component: () => import('../views/purchase/approvalPurchaseRequest.vue'),
                meta: { title: '审批采购申请'}
            },
            {
                path: '/approvalProcess',
                component: () => import('../views/process/approvalProcess.vue'),
                meta: { title: '采购审批流程'}
            },
            {
                path: '/acceptProcess',
                component: () => import('../views/process/acceptProcess.vue'),
                meta: { title: '采购验收流程'}
            },
            {
                path: '/acceptRequest',
                component: () => import('../views/accept/acceptRequest.vue'),
                meta: { title: '采购验收流程'}
            },
            {
                path: '/accept',
                component: () => import('../views/accept/accept.vue'),
                meta: { title: '采购验收流程'}
            },
            {
                path: '/DataAnalysis',
                component: () => import('../views/Data/DataAnalysis.vue'),
                meta: { title: '数据图表'}
            },
            {
                path: '/getAssets',
                component: () => import('../views/asset/Assets.vue'),
                meta: { title: '查看资产'}
            },
            {
                path: '/approvalState',
                component: () => import('../views/asset/useRequest.vue'),
                meta: { title: '查看出库审批状态'}
            },
            {
                path: '/approvalRequest',
                component: () => import('../views/asset/ApprovaluseRequest.vue'),
                meta: { title: '出库审批'}
            },
            
            {
                path: '/403',
                component: () => import(/* webpackChunkName: "403" */ '../views/403.vue'),
                meta: { title: '403' }
            },
        ]
    },
    {
        path: '/login',
        name: 'login',
        component: () => import('../views/Login.vue'),
        meta: { title: '登录', active: 2 }
    },
];

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
});

export default router
