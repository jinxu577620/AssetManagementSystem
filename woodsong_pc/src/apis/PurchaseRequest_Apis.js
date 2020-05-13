const PurchaseRequest_Apis = {
    //查看可处理的采购申请
    getPurchaseRequsetByA:{
        method:"post",
        url:'/purchase-request/getByA'
    },
    //查看审批状态
    getPurchaseRequsetByUid:{
        method:"post",
        url:'/purchase-request/getByUid'
    },
    //申请
    addPurchaseRequest:{
        method:"post",
        url:'/purchase-request/add'
    },
    //审批同意或拒绝
    approvalDicide:{
        method:"post",
        url:'/approval-process/dicide'
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
