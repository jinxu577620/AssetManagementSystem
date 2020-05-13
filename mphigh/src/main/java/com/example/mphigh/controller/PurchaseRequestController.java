package com.example.mphigh.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mphigh.entity.PurchaseRequest;
import com.example.mphigh.result.Result;
import com.example.mphigh.service.PurchaseRequestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;


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

    
    @PostMapping(value = "/add")
    public Result add(PurchaseRequest purchaseRequest) {
        System.out.println("purchaseRequest");
        purchaseRequestService.save(purchaseRequest);
        return Result.success();
    }
    @PostMapping(value = "/getByA")
    public Result getByA(Integer pageIndex, Integer pageSize) {
        return purchaseRequestService.getByA(pageIndex,pageSize);
    }
    @PostMapping(value = "/getByUid")
    public Result getByUid(String uid,Integer pageIndex, Integer pageSize) {
        return purchaseRequestService.getByUid(uid,pageIndex,pageSize);
    }
    @PostMapping(value="/dicide")
    public Result dicide(boolean dicded,String rid){  
        //更改状态

        return Result.success();
    }
    

}
