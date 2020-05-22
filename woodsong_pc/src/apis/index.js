import axios from 'axios'
import { Loading, Message} from 'element-ui';
import router from '../router'
import store from '../store'

let allApis = {};

//自动扫描子模块api并导入
const apisContext = require.context('./', false, /\.js$/);
apisContext.keys().forEach(api => {
    //如果是根目录的 index.js 不处理
    if (api.startsWith('./index')){
        return;
    }
    const apiModule = apisContext(api)
    // 兼容 import export 和 require module.export两种规范
    Object.assign(allApis, (apiModule.default || apiModule));
});

var baseUrl = 'http://127.0.0.1:8080/';

let instance = axios.create({
    baseURL: baseUrl,  // api 在 vue.config.js 中配置了代理地址，从而解决了跨域问题
    timeout: 1000
})

const index = {};  //包裹请求方法的容器

// 请求格式、参数的统一
for(let key in allApis){
    let api = allApis[key]   // method url
    // async 同步，让异步请求一个个执行，前一个异步请求返回结果给后一个异步请求作为参数再请求
    // 这个作用就避免了异步嵌套，进入回调地狱
    // let res = await axios.get('url')  等待请求完毕
    // let res2 = await axios.get('url',{res.data.xxx})
    index[key] = async function(
        params, // 请求参数
        config={}  // 配置参数
    ){
        // 不同请求方法的判断
        let response = {};  // 请求的返回值
        if(api.method === 'post' || api.method === 'put' || api.method === 'patch'){
            let newParams = new FormData();
            for(let key in params){
                newParams.append(key, params[key])
            }
            // 时间戳，解决缓存问题
            newParams.append('t', Date.parse(new Date()) / 1000);
            try{
                response = await instance[api.method](api.url, newParams, config);
            }catch(err){
                return Promise.reject(err);
            }
        }else{
            config.params = {
                t: Date.parse(new Date()) / 1000,  // 时间戳，解决缓存问题
                ...params
            };
            try{
                response = await instance[api.method](api.url, config)
            }catch(err){
                return Promise.reject(err);
            }
        }
        return response
    }
}
let loading;
// 拦截器的添加
instance.interceptors.request.use(
    config => {
        // 发起请求前
        loading = Loading.service({
            lock: true,
            text: 'Loading',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0)'
        });
        // 登录流程控制中，根据本地是否存在token判断用户的登录情况
        // 但是即使token存在，也有可能token是过期的，所以在每次的请求头中携带token
        // 后台根据携带的token判断用户的登录情况，并返回给我们对应的状态码
        // 而后我们可以在响应拦截器中，根据状态码进行一些统一的操作。
        const token = sessionStorage.token;
        token && (config.headers.common['token'] = token);

        return config
    },
    () => {
        loading.close();
        Message.error('请求错误，请求稍后重试')
    }
)
instance.interceptors.response.use(
    res => {
        loading.close();
        if (res.data.code === 0) {
            return Promise.resolve(res.data);  // 后面要用到的就只有data
        }else if (res.data.code === 500003) {    // token 过期
            setTimeout(()=>{
                store.commit('LOGOUT');
                router.push("/login");
            },1000);
            return Promise.reject(res.data.msg);
        }else{
            console.log(res.data.msg)
            return Promise.reject(res.data.msg);
        }
    },
    err => {
        if (err && err.response) {
            switch (err.response.status) {
                case 400: err.message = '请求错误(400)' ; break;
                case 401: err.message = '未授权，请重新登录(401)'; break;
                case 403: err.message = '拒绝访问(403)'; break;
                case 404: err.message = '请求出错(404)'; break;
                case 408: err.message = '请求超时(408)'; break;
                case 500: err.message = '服务器错误(500)'; break;
                case 501: err.message = '服务未实现(501)'; break;
                case 502: err.message = '网络错误(502)'; break;
                case 503: err.message = '服务不可用(503)'; break;
                case 504: err.message = '网络超时(504)'; break;
                case 505: err.message = 'HTTP版本不受支持(505)'; break;
                default: err.message = `连接出错(${err.response.status})!`;
            }
        }else{
            err.message = '连接服务器失败!'
        }
        loading.close();
        // Message.error(err.message)
        return Promise.reject(err.message);
    }
)

export default index
