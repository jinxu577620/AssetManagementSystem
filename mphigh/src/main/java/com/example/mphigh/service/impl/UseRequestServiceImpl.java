package com.example.mphigh.service.impl;

import com.example.mphigh.entity.UseRequest;
import com.example.mphigh.entity.User;
import com.example.mphigh.mapper.UseRequestMapper;
import com.example.mphigh.result.Result;
import com.example.mphigh.service.UseRequestService;
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
public class UseRequestServiceImpl extends ServiceImpl<UseRequestMapper, UseRequest> implements UseRequestService {
    
    @Autowired
    UseRequestMapper useRequestMapper;
    
    @Override
    public Result get(String uid, String amauthority, Integer pageIndex, Integer pageSize) {
        Page<UseRequest> page = new Page<>(pageIndex,pageSize);
        IPage<UseRequest> useRequestIPage;
        if(amauthority.equals("Y"))
            useRequestIPage = useRequestMapper.selectPage(page, new QueryWrapper<UseRequest>());
        else
            useRequestIPage = useRequestMapper.selectPage(page, new QueryWrapper<UseRequest>()
            .eq("uid", uid));
        return Result.success(useRequestIPage);
    }
}
