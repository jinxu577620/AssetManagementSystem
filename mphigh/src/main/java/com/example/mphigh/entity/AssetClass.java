package com.example.mphigh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 资产分类表
 * </p>
 *
 * @author jinxu18829190342@163.com
 * @since 2020-04-26
 */
@Data
@Accessors(chain = true)
public class AssetClass  {

    private static final long serialVersionUID = 1L;

    @TableId(value = "acid", type = IdType.AUTO)
    private Integer acid;

    private String acname;




}
