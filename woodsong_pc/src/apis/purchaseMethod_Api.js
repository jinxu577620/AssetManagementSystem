//zsbegin
const purchaseMethod_Api = {

    getPurchaseMethod:{
        method:"get",
        url:'/purchase-method/getPurchaseMethod'
    },
   savePurchaseMethod:{
        method:"post",
        url: '/purchase-method/savePurchaseMethod'
   },
    deletePurchaseMethod:{
        method:"delete",
        url: '/purchase-method/deletePurchaseMethod'
    }
}
export default purchaseMethod_Api
