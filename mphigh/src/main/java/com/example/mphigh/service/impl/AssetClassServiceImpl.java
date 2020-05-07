package com.example.mphigh.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mphigh.entity.AssetClass;
import com.example.mphigh.entity.PurchaseMethod;
import com.example.mphigh.mapper.AssetClassMapper;
import com.example.mphigh.params.AssetclassParam;
import com.example.mphigh.params.purchaseParam;
import com.example.mphigh.result.CodeMsg;
import com.example.mphigh.result.Result;
import com.example.mphigh.service.AssetClassService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 资产分类表 服务实现类
 * </p>
 *
 * @author jinxu18829190342@163.com
 * @since 2020-04-26
 */
@Service
public class AssetClassServiceImpl extends ServiceImpl<AssetClassMapper, AssetClass> implements AssetClassService {
    @Autowired
    AssetClassMapper assetClassMapper;

    @Override
    public Result updateassetClass(AssetclassParam param) {
        AssetClass assetClass = assetClassMapper.selectById(param.getAcid());
        BeanUtils.copyProperties(param, assetClass);
        assetClassMapper.updateById(assetClass);
        return Result.success(assetClass);
    }

    @Override
    public Result createassetClass(AssetclassParam param) {
        AssetClass assetClass = assetClassMapper.selectOne(Wrappers.<AssetClass>lambdaQuery().eq(AssetClass::getAcname, param.getAcname()));

        if(assetClass != null){
            return Result.error(CodeMsg.METHOD_IS_USED);
        }

        assetClass = new AssetClass();
        BeanUtils.copyProperties(param, assetClass);
        assetClassMapper.insert(assetClass);

        return Result.success(assetClass);
    }


    @Override
    public Result findByidandName(String acid, String acname, Integer pageIndex, Integer pageSize) {
        //设置当前页和页容量
        Page<AssetClass> page = new Page<>(pageIndex, pageSize);

        IPage<AssetClass> userIPage = assetClassMapper.selectPage(page, Wrappers.<AssetClass>lambdaQuery().like(StringUtils.isNotBlank(acid),AssetClass::getAcid, acid).like(AssetClass::getAcname,acname));

        return Result.success(userIPage);
    }

    @Override
    public void delete(AssetClass assetClass) {
        assetClassMapper.delete(assetClass);
    }
}
