const userInfo = {
    state: {
        token: window.sessionStorage.getItem('token'),
        user: JSON.parse(window.sessionStorage.getItem('user'))
        // localStorage以及sessionStorage只能是字符串形式（目前为止只支持字符串）。
        // 所以存储时是JSON对象时需要执行下JSON.stringify，所以获取时需要执行下JSON.parse
    },

    mutations: {
        //将token保存到sessionStorage里，token表示登陆状态
        SET_TOKEN: (state, data) => {
            state.token = data
            window.sessionStorage.setItem('token', data)
        },
        //设置用户名
        SET_USER: (state, data) => {
            // 把用户名存起来
            state.user = data
            window.sessionStorage.setItem('user', JSON.stringify(data))
        },
        //登出
        LOGOUT: (state) => {
            // 登出的时候要清除token
            state.token = null
            state.user = null
            window.sessionStorage.removeItem('token')
            window.sessionStorage.removeItem('user')
        }
    },
    actions: {

    }
};

export default userInfo;