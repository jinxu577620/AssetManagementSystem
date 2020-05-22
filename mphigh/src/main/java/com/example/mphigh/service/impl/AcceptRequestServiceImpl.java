package com.example.mphigh.service.impl;

import com.example.mphigh.entity.AcceptRequest;
import com.example.mphigh.entity.PurchaseRequest;
import com.example.mphigh.mapper.AcceptRequestMapper;
import com.example.mphigh.mapper.PurchaseRequestMapper;
import com.example.mphigh.params.purchaseParam;
import com.example.mphigh.result.Result;
import com.example.mphigh.service.AcceptRequestService;

import java.util.Arrays;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 验收申请表 服务实现类
 * </p>
 *
 * @author jinxu18829190342@163.com
 * @since 2020-04-26
 */
@Service
public class AcceptRequestServiceImpl extends ServiceImpl<AcceptRequestMapper, AcceptRequest> implements AcceptRequestService {
    @Autowired
    AcceptRequestMapper acceptRequestMapper;
    @Autowired
    PurchaseRequestMapper purchaseRequestMapper;
    
    @Override
    public Result getByA(Integer pageIndex, Integer pageSize) {
        Page<AcceptRequest> page = new Page<>(pageIndex,pageSize);
        IPage<AcceptRequest> acceptRequestIPage = acceptRequestMapper.selectPage(page, new QueryWrapper<AcceptRequest>());
        return Result.success(acceptRequestIPage);
    }
    @Override
    public IPage<PurchaseRequest> getByUid(String uid, Integer pageIndex, Integer pageSize) {
        Page<PurchaseRequest> page = new Page<>(pageIndex,pageSize);
        IPage<PurchaseRequest> purchaseRequestIPage = purchaseRequestMapper.selectPage(page, new QueryWrapper<PurchaseRequest>().eq("uid",uid));

        return purchaseRequestIPage;
    }

}
