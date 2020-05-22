//zsbegin
const Asset_Apis = {
    //查看资产
    getAssets:{
        method:"post",
        url:'/asset/getAssets'
    },
    getAssetData:{
        method:"get",
        url:'/asset/getAssetData'
    },
    //资产申请
    useRequest:{
        method:"post",
        url:'/use-request/add'
    },
    dicide:{
        method:"post",
        usl:'use-request/dicide'
    },
    //查看审批申请
    getUseRequest:{
        method:"post",
        url:'/use-request/get'
    },
    //审批
    dicided:{
        method:"post",
        url:'/use-request/dicide'
    },
    download:{
        method:"get",
        url:'/accept-request/download'

    },
}
export default Asset_Apis
