const Customer_Apis = {

    // 创建或修改客户
    saveCustomer:{
        method:"post",
        url:'/customer/saveCustomer'
    },

    // 获得客户
    getMyCustomers:{
        method:"get",
        url:'/customer/getMyCustomers'
    },

    getExportCustomers: {
        method:"get",
        url:'/customer/getExportCustomers'
    },

    // 获得客户
    getAllCustomers:{
        method:"get",
        url:'/customer/getAllCustomers'
    },

    // 延长跟单客户
    delayCustomer:{
        method:"put",
        url:'/customer/delayCustomer'
    },

    // 获得客户
    getCustomers:{
        method:"get",
        url:'/customer/getCustomers'
    },

    // 跟单客户
    dealCustomer:{
        method:"put",
        url:'/customer/dealCustomer'
    },

    // 删除客户
    removeCustomer:{
        method:"delete",
        url:'/customer/removeCustomer'
    },

    // 彻底删除客户
    deleteCustomer:{
        method:"delete",
        url:'/customer/deleteCustomer'
    },

}

export default Customer_Apis
