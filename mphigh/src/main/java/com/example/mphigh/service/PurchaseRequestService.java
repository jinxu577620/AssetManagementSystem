package com.example.mphigh.service;

import com.example.mphigh.entity.PurchaseRequest;
import com.example.mphigh.result.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jinxu18829190342@163.com
 * @since 2020-04-26
 */
public interface PurchaseRequestService extends IService<PurchaseRequest> {
    Result getByA(Integer pageIndex, Integer pageSize);
    Result getByUid(String uid, Integer pageIndex, Integer pageSize);
}
