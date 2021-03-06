package com.example.mphigh.service;

import com.example.mphigh.entity.Asset;
import com.example.mphigh.result.Result;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 资产表 服务类
 * </p>
 *
 * @author jinxu18829190342@163.com
 * @since 2020-04-26
 */
public interface AssetService extends IService<Asset> {
    Result getAssets(String aname,String acname,String uid,
    String department,String astate,Integer pageIndex,Integer pageSize);

    Map<String, Object> getShowData();
}
