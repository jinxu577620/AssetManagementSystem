package com.example.mphigh.service.impl;

import com.example.mphigh.entity.PurchaseRequest;
import com.example.mphigh.mapper.PurchaseRequestMapper;
import com.example.mphigh.result.Result;
import com.example.mphigh.service.PurchaseRequestService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jinxu18829190342@163.com
 * @since 2020-04-26
 */
@Service
public class PurchaseRequestServiceImpl extends ServiceImpl<PurchaseRequestMapper, PurchaseRequest> implements PurchaseRequestService {
    @Autowired
    PurchaseRequestMapper purchaseRequestMapper;
    
    @Override
    public Result getByA(Integer pageIndex, Integer pageSize) {
        Page<PurchaseRequest> page = new Page<>(pageIndex,pageSize);
        IPage<PurchaseRequest> purchaseRequestIPage = purchaseRequestMapper.selectPage(page, new QueryWrapper<PurchaseRequest>().notLike("rstate", 0).notLike("rstate", -1));
        return Result.success(purchaseRequestIPage);
    }
    @Override
    public Result getByUid(String uid, Integer pageIndex, Integer pageSize) {
        Page<PurchaseRequest> page = new Page<>(pageIndex,pageSize);
        IPage<PurchaseRequest> purchaseRequestIPage = purchaseRequestMapper.selectPage(page, new QueryWrapper<PurchaseRequest>().eq("uid",uid));
        return Result.success(purchaseRequestIPage);
    }
}
