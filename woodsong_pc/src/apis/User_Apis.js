const User_Apis = {

    // 登录
    userLogin:{
        method:"post",
        url:'/user/login'
    },

    // 获得用户
    getUsers:{
        method:"get",
        url:'/user/getUsers'
    },

    // 创建或修改用户
    saveUser:{
        method:"post",
        url:'/user/saveUser'
    },


    // 冻结用户
    freezeUser:{
        method:"put",
        url:'/user/freezeUser'
    },

    // 解冻用户
    unFreezeUser:{
        method:"put",
        url:'/user/unFreezeUser'
    },

    // 删除用户
    deleteUser:{
        method:"delete",
        url:'/user/deleteUser'
    },

    // 退出
    logout:{
        method:"get",
        url:'/user/logout'
    }
}

export default User_Apis
