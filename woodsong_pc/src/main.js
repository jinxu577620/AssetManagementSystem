import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import './plugins/element.js'
import './assets/css/icon.css';
import 'babel-polyfill'

Vue.config.productionTip = false

// api请求接口
import apis from './apis/index.js';
Vue.prototype.$apis = apis;

// 上传oss接口
import oss from './utils/ossUtil'
Vue.prototype.$oss = oss;

// 将md5注入到vue原型上
import md5 from 'js-md5';
Vue.prototype.$md5 = md5;

//使用钩子函数对路由进行权限跳转
router.beforeEach((to, from, next) => {
    document.title = `${to.meta.title} | 资产管理系统`;
    // 查看是否已经登录
    let user = store.state.userInfo.user;
    // 没有登录，请求的不是登录页面，则跳转到登录页面
    if (!user && to.path !== '/login') {
        next('/login');
    }
    //
    else if (to.meta.permission) {
        // 如果是管理员权限则可进入，这里只是简单的模拟管理员权限而已
        user.role < 2 ? next() : next('/403');
    } else {
        // 简单的判断IE10及以下不进入富文本编辑器，该组件不兼容
        if (navigator.userAgent.indexOf('MSIE') > -1 && to.path === '/editor') {
            Vue.prototype.$alert('vue-quill-editor组件不兼容IE10及以下浏览器，请使用更高版本的浏览器查看', '浏览器不兼容通知', {
                confirmButtonText: '确定'
            });
        } else {
            next();
        }
    }
});

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
