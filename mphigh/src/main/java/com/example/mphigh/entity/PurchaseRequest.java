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
 * 
 * </p>
 *
 * @author jinxu18829190342@163.com
 * @since 2020-04-26
 */
@Data
@Accessors(chain = true)
public class PurchaseRequest  {

    private static final long serialVersionUID = 1L;

    @TableId(value = "prid", type = IdType.AUTO)
    private Integer prid;

    private String aname;

    private String aclass;

    private Integer acid;

    private Integer num;

    private Double budget;

    private String uid;

    private String uname;

    private Integer pmid;

    private String pmname;

    private Integer rstate;

    private Integer pid;

    private String specification;

    private String manufacturer;


}
