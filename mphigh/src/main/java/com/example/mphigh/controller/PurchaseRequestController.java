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
        //缺少对多人申请同一物资的限制 之后有时间在改  或者在审批同意后对所有其它同一物资申请进行拒绝
        //解决方案  多人申请同一物资时 同意一个会自动拒绝其它的请求
        return Result.success();
    }*/
    @PostMapping(value = "/get")
    public Result get(Integer pageIndex, Integer pageSize) {
        return purchaseRequestService.get(pageIndex,pageSize);
    }
}
