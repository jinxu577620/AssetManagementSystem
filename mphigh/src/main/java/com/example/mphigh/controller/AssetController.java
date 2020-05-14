package com.example.mphigh.controller;

import javax.validation.constraints.NotNull;

import com.example.mphigh.result.Result;
import com.example.mphigh.service.AssetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 资产表 前端控制器
 * </p>
 *
 * @author jinxu18829190342@163.com
 * @since 2020-04-26
 */
@RestController
@RequestMapping("/asset")
public class AssetController {
    
    @Autowired
    private AssetService assetService;
    @PostMapping(value = "/getAssets")
    public Result getAssets(String aname,String acname,String uid,
    String department,String astate,Integer pageIndex,Integer pageSize) {
        return assetService.getAssets(aname,acname,uid,department,astate,pageIndex,pageSize);
    }
    @GetMapping(value = "/getAssetData")
    public  Result getAssetData(){
        System.out.println("进来了吗");
       Map<String,Object>map1=assetService.getShowData();

        List<String>department=new ArrayList<>();
        department.add("研发部");
        department.add("学生会");
        List<Integer>count=new ArrayList<>();
        count.add(4);
        count.add(2);
        Map<String,Object> map=new HashMap<>();
        map.put("department",department);
        map.put("count",count);
        return Result.success(map1);
    }


}
