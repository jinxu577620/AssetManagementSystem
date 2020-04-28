const Orders_Apis = {

    // 创建或修改订单
    saveOrders:{
        method:"post",
        url:'/orders/saveOrders'
    },

    // 获得订单
    getOrders:{
        method:"get",
        url:'/orders/getOrders'
    },

    // 获取导出订单
    getExportOrders:{
        method:"get",
        url:'/orders/getExportOrders'
    },

    // 审批通过订单
    approvalOrders:{
        method:"put",
        url:'/orders/approvalOrders'
    },

    // 申请删除订单
    applyDeleteOrders:{
        method:"put",
        url:'/orders/applyDeleteOrders'
    },

    // 取消删除订单
    cancelDeleteOrders:{
        method:"put",
        url:'/orders/cancelDeleteOrders'
    },

    // 删除订单
    removeOrders:{
        method:"delete",
        url:'/orders/removeOrders'
    },

    // 彻底删除订单
    deleteOrders:{
        method:"delete",
        url:'/orders/deleteOrders'
    },

    // 发货
    deliver:{
        method:"put",
        url:'/orders/deliver'
    },
    
}

export default Orders_Apis
