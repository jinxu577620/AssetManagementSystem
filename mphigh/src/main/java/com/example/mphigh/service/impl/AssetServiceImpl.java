package com.example.mphigh.service.impl;

import com.example.mphigh.entity.Asset;
import com.example.mphigh.mapper.AssetMapper;
import com.example.mphigh.result.Result;
import com.example.mphigh.service.AssetService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * <p>
 * 资产表 服务实现类
 * </p>
 *
 * @author jinxu18829190342@163.com
 * @since 2020-04-26
 */
@Service
public class AssetServiceImpl extends ServiceImpl<AssetMapper, Asset> implements AssetService {
    @Autowired
    AssetMapper assetMapper;
    
    @Override
    public Result getAssets(String aname, String acname, String uid,
    String department,String astate,Integer pageIndex,Integer pageSize) {
        Page<Asset> page = new Page<>(pageIndex,pageSize);
        System.out.println(aname+"dd"+acname);
        IPage<Asset> assetIPage = assetMapper.selectPage(page, new QueryWrapper<Asset>()
        .like(StringUtils.isNotEmpty(aname),"aname",aname)
        .like(StringUtils.isNotEmpty(acname),"acname",acname)
        .like(StringUtils.isNotEmpty(uid),"uid",uid)
        .like(StringUtils.isNotEmpty(department),"department", department)
        .like(StringUtils.isNotEmpty(astate),"astate",astate));
        return Result.success(assetIPage);

    }
}
