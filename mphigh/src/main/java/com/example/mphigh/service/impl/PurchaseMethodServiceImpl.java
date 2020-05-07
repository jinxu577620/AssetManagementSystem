package com.example.mphigh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mphigh.entity.PurchaseMethod;
import com.example.mphigh.entity.User;
import com.example.mphigh.enums.UserStatus;
import com.example.mphigh.mapper.PurchaseMethodMapper;
import com.example.mphigh.params.purchaseParam;
import com.example.mphigh.result.CodeMsg;
import com.example.mphigh.result.Result;
import com.example.mphigh.service.PurchaseMethodService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 采购方式表 服务实现类
 * </p>
 *
 * @author jinxu18829190342@163.com
 * @since 2020-04-26
 */
@Service
public class PurchaseMethodServiceImpl extends ServiceImpl<PurchaseMethodMapper, PurchaseMethod> implements PurchaseMethodService {
    @Autowired
    PurchaseMethodMapper purchaseMethodMapper;

    @Override
    public Result createPurchaseMethod(purchaseParam param) {
        PurchaseMethod purchaseMethod = purchaseMethodMapper.selectOne(Wrappers.<PurchaseMethod>lambdaQuery().eq(PurchaseMethod::getPmname, param.getPmname()));

        if(purchaseMethod != null){
            return Result.error(CodeMsg.METHOD_IS_USED);
        }

        purchaseMethod = new PurchaseMethod();
        BeanUtils.copyProperties(param, purchaseMethod);
        purchaseMethodMapper.insert(purchaseMethod);

        return Result.success(purchaseMethod);
    }

    @Override
    public void delete(PurchaseMethod purchaseMethod) {
        purchaseMethodMapper.delete(purchaseMethod);
    }

    @Override
    public Result updatePurchaseMethod(purchaseParam param) {

        PurchaseMethod purchaseMethod = purchaseMethodMapper.selectById(param.getPmid());
        BeanUtils.copyProperties(param, purchaseMethod);
        purchaseMethodMapper.updateById(purchaseMethod);
        return Result.success(purchaseMethod);
    }
    @Override
    public Result findByidandName(String pmid, String pmname, Integer pageIndex, Integer pageSize) {

            //设置当前页和页容量
            Page<PurchaseMethod> page = new Page<>(pageIndex, pageSize);

            IPage<PurchaseMethod> userIPage = purchaseMethodMapper.selectPage(page, Wrappers.<PurchaseMethod>lambdaQuery().like(StringUtils.isNotBlank(pmid),PurchaseMethod::getPmid, pmid).like(PurchaseMethod::getPmname,pmname));

            return Result.success(userIPage);
    }

}
