package com.example.mphigh.service;

import com.example.mphigh.entity.PurchaseMethod;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mphigh.params.purchaseParam;
import com.example.mphigh.result.Result;

/**
 * <p>
 * 采购方式表 服务类
 * </p>
 *
 * @author jinxu18829190342@163.com
 * @since 2020-04-26
 */
public interface PurchaseMethodService extends IService<PurchaseMethod> {


    Result findByidandName(String pmid, String pmname, Integer pageIndex, Integer pageSize);

    Result updatePurchaseMethod(purchaseParam param);

    void delete(PurchaseMethod purchaseMethod);

    Result createPurchaseMethod(purchaseParam param);
}
