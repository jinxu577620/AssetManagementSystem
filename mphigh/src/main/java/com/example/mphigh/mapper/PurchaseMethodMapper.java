package com.example.mphigh.mapper;

import com.example.mphigh.entity.PurchaseMethod;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mphigh.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 采购方式表 Mapper 接口
 * </p>
 *
 * @author jinxu18829190342@163.com
 * @since 2020-04-26
 */
public interface PurchaseMethodMapper extends BaseMapper<PurchaseMethod> {
    @Delete("delete from purchase_method where pmid = #{purchaseMethod.pmid}")
    void delete(@Param("purchaseMethod") PurchaseMethod purchaseMethod);
}
