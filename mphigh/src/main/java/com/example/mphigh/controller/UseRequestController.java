package com.example.mphigh.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mphigh.entity.Asset;
import com.example.mphigh.entity.UseRequest;
import com.example.mphigh.entity.User;
import com.example.mphigh.result.Result;
import com.example.mphigh.service.AssetService;
import com.example.mphigh.service.UseRequestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.handler.UserRoleAuthorizationInterceptor;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jinxu18829190342@163.com
 * @since 2020-04-26
 */
@RestController
@RequestMapping("/use-request")
public class UseRequestController {
    @Autowired
    private UseRequestService useRequestService;
    @Autowired
    private AssetService assetService;

    
    @PostMapping(value = "/add")
    public Result add(UseRequest useRequest) {
        useRequestService.save(useRequest);
        //缺少对多人申请同一物资的限制 之后有时间在改  或者在审批同意后对所有其它同一物资申请进行拒绝
        //解决方案  多人申请同一物资时 同意一个会自动拒绝其它的请求
        return Result.success();
    }
    @PostMapping(value = "/get")
    public Result get(String uid, String amauthority, Integer pageIndex, Integer pageSize) {
        
        return useRequestService.get(uid, amauthority, pageIndex, pageSize);
    }
    @PostMapping(value = "/dicide")
    public Result dicide(boolean dicide,Integer rid) {
        System.out.println("getget");
        if(dicide==false){
            UseRequest useRequest = useRequestService.getById(rid);
            useRequest.setRstate("已拒绝");
            useRequestService.updateById(useRequest);
        }
        else{
            UseRequest useRequest = useRequestService.getById(rid);
            Asset asset = assetService.getById(useRequest.getAid());
            asset.setAstate("使用");
            asset.setUid(useRequest.getUid());
            asset.setDepartment(useRequest.getDepartment());
            assetService.updateById(asset);
            useRequest.setRstate("已通过");
            useRequestService.updateById(useRequest);
            HashMap mapTem = new HashMap<String,Integer>();
            mapTem.put("aid", useRequest.getAid());
            List<UseRequest> requestList = useRequestService.listByMap(mapTem);
            System.out.println(requestList.size());
            for (UseRequest request : requestList) {
                if(request.getRstate().equals("审批中"))
                    request.setRstate("已拒绝");
                System.out.println(request.getRid()+request.getRstate());
                useRequestService.updateById(request);
            }
        }
        return Result.success();
    }

}
