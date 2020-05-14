package com.example.mphigh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色
 * </p>
 *
 * @author jinxu18829190342@163.com
 * @since 2020-04-23
 */
@Data
@Accessors(chain = true)
public class Character  {

    private static final long serialVersionUID = 1L;

    @TableId(value = "cid", type = IdType.AUTO)
    private Integer cid;

    private String cname;

    private Integer did;

    private String smauthority;

    private String umauthority;

    private String amauthority;

    private String pauthority;

    private String apauthority;


 }
