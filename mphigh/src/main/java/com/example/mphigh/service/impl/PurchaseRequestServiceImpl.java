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
    public Result get(Integer pageIndex, Integer pageSize) {
        Page<PurchaseRequest> page = new Page<>(pageIndex,pageSize);
        IPage<PurchaseRequest> purchaseRequestIPage = purchaseRequestMapper.selectPage(page, new QueryWrapper<PurchaseRequest>());
        return Result.success(purchaseRequestIPage);
    }
}
