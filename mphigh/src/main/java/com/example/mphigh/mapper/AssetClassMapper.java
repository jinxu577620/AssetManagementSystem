package com.example.mphigh.mapper;

import com.example.mphigh.entity.AssetClass;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mphigh.entity.PurchaseMethod;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 资产分类表 Mapper 接口
 * </p>
 *
 * @author jinxu18829190342@163.com
 * @since 2020-04-26
 */
public interface AssetClassMapper extends BaseMapper<AssetClass> {
    @Delete("delete from asset_class where acid = #{assetClass.acid}")
    void delete(@Param("assetClass") AssetClass assetClass);
}
