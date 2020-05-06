package com.example.mphigh.service;

import com.example.mphigh.entity.AssetClass;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mphigh.params.AssetclassParam;
import com.example.mphigh.params.purchaseParam;
import com.example.mphigh.result.Result;

/**
 * <p>
 * 资产分类表 服务类
 * </p>
 *
 * @author jinxu18829190342@163.com
 * @since 2020-04-26
 */
public interface AssetClassService extends IService<AssetClass> {

    Result updateassetClass(AssetclassParam param);

    Result createassetClass(AssetclassParam param);

    Result findByidandName(String acid, String acname, Integer pageIndex, Integer pageSize);

    void delete(AssetClass assetClass);
}
