const PurchaseRequest_Apis = {
    //查看所有采购申请
    getPurchaseRequsetByA:{
        method:"post",
        url:'/purchase-request/getByA'
    },
    getPurchaseRequsetByUid:{
        method:"post",
        url:'/purchase-request/getByUid'
    },

    addPurchaseRequest:{
        method:"post",
        url:''
    },
    //流程的也写这算了
    //审批流程
    getApprovalProcess:{
        method:"post",
        url:'/approval-process/get'
    },
    addApprovalProcess:{
        method:"post",
        url:'/approval-process/add'
    },
    //验收流程
    getAcceptProcess:{
        method:"post",
        url:'/accept-process/get'
    },
    addAcceptProcess:{
        method:"post",
        url:'/accept-process/add'
    },
   
    
}
export default PurchaseRequest_Apis
