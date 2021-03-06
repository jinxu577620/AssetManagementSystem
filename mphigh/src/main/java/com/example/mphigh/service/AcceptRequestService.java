package com.example.mphigh.service;

import com.example.mphigh.entity.AcceptRequest;
import com.example.mphigh.entity.PurchaseRequest;
import com.example.mphigh.result.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 验收申请表 服务类
 * </p>
 *
 * @author jinxu18829190342@163.com
 * @since 2020-04-26
 */
public interface AcceptRequestService extends IService<AcceptRequest> {
    Result getByA(Integer pageIndex, Integer pageSize);
    IPage<PurchaseRequest> getByUid(String uid, Integer pageIndex, Integer pageSize);
}
