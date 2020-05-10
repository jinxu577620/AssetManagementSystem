const PurchaseRequest_Apis = {
    //查看所有采购申请
    getPurchaseRequset:{
        method:"post",
        url:'/purchase-request/get'
    },
    addPurchaseRequest:{
        method:"post",
        url:''
    },
    //流程的也写这算了
    getPurchaseProcess:{
        method:"post",
        url:''
    },
    addPurchaseProcess:{
        method:"post",
        url:''
    },

    //资产申请
    /*useRequest:{
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
    },*/
    //审批
    
}
export default PurchaseRequest_Apis
