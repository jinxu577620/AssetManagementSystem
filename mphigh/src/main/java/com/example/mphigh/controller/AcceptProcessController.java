package com.example.mphigh.controller;

import com.example.mphigh.entity.AcceptProcess;
import com.example.mphigh.result.CodeMsg;
import com.example.mphigh.result.Result;
import com.example.mphigh.service.AcceptProcessService;
import com.example.mphigh.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 验收流程表 前端控制器
 * </p>
 *
 * @author jinxu18829190342@163.com
 * @since 2020-05-09
 */
@RestController
@RequestMapping("/accept-process")
public class AcceptProcessController {
    @Autowired
    private AcceptProcessService acceptProcessService;
    @Autowired
    private UserService userService;
    
    
    @PostMapping(value = "/add")
    public Result add(AcceptProcess acceptProcess) {
        //检测工号是否存在
        boolean flag = true;
        if(!acceptProcess.getUid1().isEmpty() && (userService.getById(acceptProcess.getUid1()) == null || userService.getById(acceptProcess.getUid1()).getApauthority().equals("N")))
            flag = false;  //满足说明输错了
        else if(!acceptProcess.getUid2().isEmpty() && (userService.getById(acceptProcess.getUid2()) == null || userService.getById(acceptProcess.getUid2()).getApauthority().equals("N")))
            flag = false;
        else if(!acceptProcess.getUid3().isEmpty() && (userService.getById(acceptProcess.getUid3()) == null || userService.getById(acceptProcess.getUid3()).getApauthority().equals("N")))
            flag = false;
        else if(!acceptProcess.getUid4().isEmpty() && (userService.getById(acceptProcess.getUid4()) == null || userService.getById(acceptProcess.getUid4()).getApauthority().equals("N")))
            flag = false;
        else if(!acceptProcess.getUid5().isEmpty() && (userService.getById(acceptProcess.getUid5()) == null || userService.getById(acceptProcess.getUid5()).getApauthority().equals("N")))
            flag = false;
        else if(!acceptProcess.getUid6().isEmpty() && (userService.getById(acceptProcess.getUid6()) == null || userService.getById(acceptProcess.getUid6()).getApauthority().equals("N")))
            flag = false;
            
        if(flag == true){
            acceptProcessService.save(acceptProcess);
            return Result.success();
        }
        else
            return Result.error(CodeMsg.NO_USER);
    }
    @PostMapping(value = "/get")
    public Result get() {
        System.out.println("daozhezs");
        return Result.success(acceptProcessService.list());
    }
}
