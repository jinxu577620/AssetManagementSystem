package com.example.mphigh.controller;


import com.example.mphigh.entity.AssetClass;
import com.example.mphigh.entity.PurchaseMethod;
import com.example.mphigh.params.AssetclassParam;
import com.example.mphigh.params.purchaseParam;
import com.example.mphigh.result.CodeMsg;
import com.example.mphigh.result.Result;
import com.example.mphigh.service.AssetClassService;
import com.example.mphigh.service.PurchaseMethodService;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 资产分类表 前端控制器
 * </p>
 *
 * @author jinxu18829190342@163.com
 * @since 2020-04-26
 */
@RestController
@RequestMapping("/asset-class")
public class AssetClassController {

    @Autowired
    AssetClassService assetClassService;
    @GetMapping(value = "/getassetClass")
    public Result getPurchaseMethod(
            String acid,String acname,
            @RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        return assetClassService.findByidandName(acid,acname, pageIndex, pageSize);
    }
    @PostMapping(value = "/saveassetClass")
    public Result savePurchaseMethod(@Valid AssetclassParam param, HttpServletRequest request) {
        AssetClass assetClass = assetClassService.getById(param.getAcid());
        System.out.println("进来了吗");
        // id不为空，则修改
        if(assetClass!=null){
            return  assetClassService.updateassetClass(param);
        }else{
            return assetClassService.createassetClass(param);
        }

    }
    @DeleteMapping(value = "/deleteassetClass")
    public Result deleteUser(@NotNull(message = "Id不能为空") @Length(min=32, max=32) String acid){
        AssetClass assetClass = assetClassService.getById(acid);
        System.out.println("进来了吗");
        if (assetClass == null){
            return Result.error(CodeMsg.NO_USER);
        }
        assetClassService.delete(assetClass);
        return Result.success();
    }
}
