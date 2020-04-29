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
                path: '/createUser',
                component: () => import('../views/user/CreateUser.vue'),
                meta: { title: '创建用户', permission: true }
            },
            {
                path: '/userList',
                component: () => import('../views/user/UserList.vue'),
                meta: { title: '用户列表', permission: true}
            },
            {
                path: '/createOrders',
                component: () => import('../views/orders/CreateOrders.vue'),
                meta: { title: '创建订单'}
            },
            {
                path: '/pendingApprovalOrders',
                component: () => import('../views/orders/PendingApprovalOrders.vue'),
                meta: { title: '待审批订单'}
            },
            {
                path: '/pendingSendOrders',
                component: () => import('../views/orders/PendingSendOrders.vue'),
                meta: { title: '待发货订单'}
            },
            {
                path: '/pendingDeleteOrders',
                component: () => import('../views/orders/PendingDeleteOrders.vue'),
                meta: { title: '待删除订单'}
            },
            {
                path: '/deletedOrders',
                component: () => import('../views/orders/DeletedOrders.vue'),
                meta: { title: '已删除订单'}
            },
            {
                path: '/pendingDeliverOrders',
                component: () => import('../views/orders/PendingDeliverOrders.vue'),
                meta: { title: '待发货订单'}
            },
            {
                path: '/shippedOrders',
                component: () => import('../views/orders/ShippedOrders.vue'),
                meta: { title: '已发货订单'}
            },
            {
                path: '/getAssets',
                component: () => import('../views/orders/Assets.vue'),
                meta: { title: '查看资产'}
            },

            {
                path: '/createCustomer',
                component: () => import('../views/customer/CreateCustomer.vue'),
                meta: { title: '创建客户'}
            },
            {
                path: '/myCustomers',
                component: () => import('../views/customer/MyCustomers.vue'),
                meta: { title: '我的客户'}
            },
            {
                path: '/commonCustomers',
                component: () => import('../views/customer/CommonCustomers.vue'),
                meta: { title: '客户池'}
            },
            {
                path: '/allCustomers',
                component: () => import('../views/customer/AllCustomers.vue'),
                meta: { title: '所有客户', permission: true}
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
