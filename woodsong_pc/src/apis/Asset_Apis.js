//zsbegin
const Asset_Apis = {
    //查看资产
    getAssets:{
        method:"post",
        url:'/asset/getAssets'
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
    dicided:{
        method:"post",
        url:'/use-request/dicide'
    },
    //审批
    
}
export default Asset_Apis
