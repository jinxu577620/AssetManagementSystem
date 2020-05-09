package com.example.mphigh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 验收流程表
 * </p>
 *
 * @author jinxu18829190342@163.com
 * @since 2020-05-09
 */
@Data
@Accessors(chain = true)
public class AcceptProcess {

    private static final long serialVersionUID = 1L;

    @TableId(value = "pid", type = IdType.AUTO)
    private Integer pid;

    private String pname;

    /**
     * 第一审核人
     */
    private String uid1;

    private String uid2;

    private String uid3;

    private String uid4;

    private String uid5;

    private String uid6;


}
