package com.example.mphigh.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mphigh.entity.PurchaseRequest;
import com.example.mphigh.result.Result;
import com.example.mphigh.service.PurchaseRequestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jinxu18829190342@163.com
 * @since 2020-04-26
 */
@RestController
@RequestMapping("/purchase-request")
public class PurchaseRequestController {
    @Autowired
    private PurchaseRequestService purchaseRequestService;

    
    /*@PostMapping(value = "/add")
    public Result add(UseRequest useRequest) {
        useRequestService.save(useRequest);
        
        return Result.success();
    }*/
    @PostMapping(value = "/get")
    public Result get(Integer pageIndex, Integer pageSize) {
        return purchaseRequestService.get(pageIndex,pageSize);
    }
}
