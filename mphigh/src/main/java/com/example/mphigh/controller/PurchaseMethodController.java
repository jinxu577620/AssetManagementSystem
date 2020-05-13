package com.example.mphigh.controller;


import cn.hutool.core.util.StrUtil;
import com.example.mphigh.entity.PurchaseMethod;
import com.example.mphigh.entity.User;
import com.example.mphigh.params.UserParam;
import com.example.mphigh.params.purchaseParam;
import com.example.mphigh.result.CodeMsg;
import com.example.mphigh.result.Result;
import com.example.mphigh.service.PurchaseMethodService;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 采购方式表 前端控制器
 * </p>
 *
 * @author jinxu18829190342@163.com
 * @since 2020-04-26
 */
@RestController
@RequestMapping("/purchase-method")
public class PurchaseMethodController {
    @Autowired
    PurchaseMethodService purchaseMethodService;
    @GetMapping(value = "/getPurchaseMethod")
    public Result getPurchaseMethod(
            String pmid,String pmname,
            @RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        return purchaseMethodService.findByidandName(pmid,pmname, pageIndex, pageSize);
    }
    @PostMapping(value = "/savePurchaseMethod")
    public Result savePurchaseMethod(@Valid purchaseParam param, HttpServletRequest request) {
        PurchaseMethod operateUser = purchaseMethodService.getById(param.getPmid());
        // id不为空，则修改
        if(operateUser!=null){
            return  purchaseMethodService.updatePurchaseMethod(param);
        }else{
            return purchaseMethodService.createPurchaseMethod(param);
        }

    }
    @DeleteMapping(value = "/deletePurchaseMethod")
    public Result deleteUser(@NotNull(message = "Id不能为空") @Length(min=32, max=32) String pmid){
        PurchaseMethod purchaseMethod = purchaseMethodService.getById(pmid);
        System.out.println("进来了吗");
        if (purchaseMethod == null){
            return Result.error(CodeMsg.NO_USER);
        }
        purchaseMethodService.delete(purchaseMethod);
        return Result.success();
    }

}
