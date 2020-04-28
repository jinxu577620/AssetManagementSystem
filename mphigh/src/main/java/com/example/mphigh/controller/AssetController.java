package com.example.mphigh.controller;

import javax.validation.constraints.NotNull;

import com.example.mphigh.result.Result;
import com.example.mphigh.service.AssetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
    public Result getAssets(Integer pageIndex,Integer pageSize) {
        System.err.println("到controller了");
        return assetService.getAssets(pageIndex,pageSize);
    }


}
