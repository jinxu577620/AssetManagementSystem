package com.example.mphigh.controller;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.mphigh.entity.AcceptProcess;
import com.example.mphigh.entity.AcceptRequest;
import com.example.mphigh.entity.Asset;
import com.example.mphigh.entity.PurchaseRequest;
import com.example.mphigh.result.CodeMsg;
import com.example.mphigh.result.Result;
import com.example.mphigh.service.AcceptProcessService;
import com.example.mphigh.service.AcceptRequestService;
import com.example.mphigh.service.AssetService;
import com.example.mphigh.service.PurchaseRequestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 验收申请表 前端控制器
 * </p>
 *
 * @author jinxu18829190342@163.com
 * @since 2020-04-26
 */
@RestController
@RequestMapping("/accept-request")
public class AcceptRequestController {
    @Autowired
    private AcceptRequestService acceptRequestService;
    private AcceptProcessService acceptProcessService;
    private PurchaseRequestService purchaseRequestService;
    private AssetService assetService;
    @PostMapping("/upload")
    @ResponseBody
    public Result upload(@RequestParam("file") MultipartFile file,String rid) {
        if (file.isEmpty()) {
            return Result.error(CodeMsg.UPLOAD_ERROR);
        }

        String fileName = file.getOriginalFilename();
        String filePath = "/temp/" + rid;
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
            return Result.success(filePath + fileName);
        } catch (IOException e) {
        }
        return Result.error(CodeMsg.UPLOAD_ERROR);
    }
    @PostMapping(value = "/add")
    public Result add(AcceptRequest acceptRequest) {
        //先加构造函数
        /*
        rid   要跟采购申请的一样
        aname   跟采购申请一样
        acid    跟采购申请一样
        num      输入
        price   输入
        annex   其它地方实际处理  这里给个名字或者路径
        supplier  输入
        evaluate    输入
        astate      自动
        pid         选择
        manfacturer 输入
        */
        acceptRequestService.save(acceptRequest);
        return Result.success();
    }
    @PostMapping(value = "/getByA")
    public Result getByA(Integer pageIndex, Integer pageSize) {
        return acceptRequestService.getByA(pageIndex,pageSize);
    }
    @PostMapping(value = "/getByUid")
    public Result getByUid(String uid,Integer pageIndex, Integer pageSize) {
        IPage<PurchaseRequest> purchaseRequestIpage = acceptRequestService.getByUid(uid,pageIndex,pageSize);
        List<AcceptRequest> acceptRequestList = Arrays.asList();
        for(PurchaseRequest purchaseRequest: purchaseRequestIpage.getRecords())
            if(uid.equals(purchaseRequest.getUid()))
                acceptRequestList.add(acceptRequestService.getById(purchaseRequest.getPrid()));
        return Result.success(acceptRequestList);
    }
    @PostMapping(value="/dicide")
    public Result dicide(boolean dicide,String rid,Date eDate, String place){
        //更改状态
        AcceptRequest acceptRequest = acceptRequestService.getById(rid);
        PurchaseRequest purchaseRequest = purchaseRequestService.getById(rid);
        if(dicide == false)
            acceptRequest.setAstate(-1);
        else{
            AcceptProcess acceptProcess = acceptProcessService.getById(acceptRequest.getPid());
            acceptRequest.setAstate(acceptRequest.getAstate()+1);
            if(acceptRequest.getAstate() == 2 && acceptProcess.getUid2().equals(""))
                acceptRequest.setAstate(0);
            else if(acceptRequest.getAstate() == 3 && acceptProcess.getUid3().equals(""))
                acceptRequest.setAstate(0);
            else if(acceptRequest.getAstate() == 4 && acceptProcess.getUid4().equals(""))
                acceptRequest.setAstate(0);
            else if(acceptRequest.getAstate() == 5 && acceptProcess.getUid5().equals(""))
                acceptRequest.setAstate(0);
            else if(acceptRequest.getAstate() == 6 && acceptProcess.getUid6().equals(""))
                acceptRequest.setAstate(0);
            else if(acceptRequest.getAstate() >= 7)
                acceptRequest.setAstate(0);
        }
        acceptRequestService.updateById(acceptRequest);
        Asset asset = new Asset();
        asset.setAname(purchaseRequest.getAname());
        asset.setAcid(purchaseRequest.getAcid());
        asset.setAcname(purchaseRequest.getAclass());
        asset.setNum(acceptRequest.getNum());
        asset.setStime(new Date()); //自动
        asset.setPrice(acceptRequest.getPrice());
        asset.setAstate("闲置");
        asset.setEtime(eDate);
        asset.setAnnex(acceptRequest.getAnnex());
        asset.setSupplier(acceptRequest.getSupplier());
        asset.setEvaluate(acceptRequest.getEvaluate());
        asset.setManufacturer(purchaseRequest.getManufacturer());
        asset.setPlace(place);
        assetService.save(asset);
        return Result.success();
    }
}
