package com.example.mphigh.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mphigh.entity.ApprovalProcess;
import com.example.mphigh.entity.PurchaseRequest;
import com.example.mphigh.result.Result;
import com.example.mphigh.service.ApprovalProcessService;
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
    @Autowired
    private ApprovalProcessService approvalProcessService;
    
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
    public Result dicide(boolean dicide,String rid){  
        //更改状态
        PurchaseRequest purchaseRequest = purchaseRequestService.getById(rid);
        if(dicide == false)
            purchaseRequest.setRstate(-1);
        else{
            ApprovalProcess approvalProcess = approvalProcessService.getById(purchaseRequest.getPid());
            purchaseRequest.setRstate(purchaseRequest.getRstate()+1);
            if(purchaseRequest.getRstate() == 2 && approvalProcess.getUid2().equals(""))
                purchaseRequest.setRstate(0);
            else if(purchaseRequest.getRstate() == 3 && approvalProcess.getUid3().equals(""))
                purchaseRequest.setRstate(0);
            else if(purchaseRequest.getRstate() == 4 && approvalProcess.getUid4().equals(""))
                purchaseRequest.setRstate(0);
            else if(purchaseRequest.getRstate() == 5 && approvalProcess.getUid5().equals(""))
                purchaseRequest.setRstate(0);
            else if(purchaseRequest.getRstate() == 6 && approvalProcess.getUid6().equals(""))
                purchaseRequest.setRstate(0);
            else if(purchaseRequest.getRstate() >= 7)
                purchaseRequest.setRstate(0);
        }
        purchaseRequestService.updateById(purchaseRequest);
        return Result.success();
    }
    

}
