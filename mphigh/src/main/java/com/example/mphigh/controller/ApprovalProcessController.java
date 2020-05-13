package com.example.mphigh.controller;

import com.example.mphigh.entity.ApprovalProcess;
import com.example.mphigh.entity.User;
import com.example.mphigh.result.CodeMsg;
import com.example.mphigh.result.Result;
import com.example.mphigh.service.ApprovalProcessService;
import com.example.mphigh.service.UserService;

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
 * @since 2020-05-09
 */
@RestController
@RequestMapping("/approval-process")
public class ApprovalProcessController {
    @Autowired
    private ApprovalProcessService approvalProcessService;
    @Autowired
    private UserService userService;
    
    
    @PostMapping(value = "/add")
    public Result add(ApprovalProcess approvalProcess) {
        //检测工号是否存在
        boolean flag = true;
        if(!approvalProcess.getUid1().isEmpty() && (userService.getById(approvalProcess.getUid1()) == null || userService.getById(approvalProcess.getUid1()).getApauthority().equals("N")))
            flag = false;  //满足说明输错了
        else if(!approvalProcess.getUid2().isEmpty() && (userService.getById(approvalProcess.getUid2()) == null || userService.getById(approvalProcess.getUid2()).getApauthority().equals("N")))
            flag = false;
        else if(!approvalProcess.getUid3().isEmpty() && (userService.getById(approvalProcess.getUid3()) == null || userService.getById(approvalProcess.getUid3()).getApauthority().equals("N")))
            flag = false;
        else if(!approvalProcess.getUid4().isEmpty() && (userService.getById(approvalProcess.getUid4()) == null || userService.getById(approvalProcess.getUid4()).getApauthority().equals("N")))
            flag = false;
        else if(!approvalProcess.getUid5().isEmpty() && (userService.getById(approvalProcess.getUid5()) == null || userService.getById(approvalProcess.getUid5()).getApauthority().equals("N")))
            flag = false;
        else if(!approvalProcess.getUid6().isEmpty() && (userService.getById(approvalProcess.getUid6()) == null || userService.getById(approvalProcess.getUid6()).getApauthority().equals("N")))
            flag = false;
            
        if(flag == true){
            approvalProcessService.save(approvalProcess);
            return Result.success();
        }
        else
            return Result.error(CodeMsg.NO_USER);
    }
    @PostMapping(value = "/get")
    public Result get() {
        return Result.success(approvalProcessService.list());
    }
}
