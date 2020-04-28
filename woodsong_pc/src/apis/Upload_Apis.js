const Upload_Apis = {

    // 获取阿里云OSS的配置，没有用STS
    getOssConfig:{
        method:"get",
        url:'/upload/getOssConfig'
    },

    // 获取阿里云OSS的配置，用STS
    getOssToken:{
        method:"get",
        url:'/upload/getOssToken'
    },

}

export default Upload_Apis
