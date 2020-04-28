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
public class UseRequest  {

    private static final long serialVersionUID = 1L;

    /**
     * 使用申请编号
     */
    @TableId(value = "rid", type = IdType.AUTO)
    private Integer rid;

    private String uid;

    private String uname;

    private Integer aid;

    private String aname;

    private String ause;

    private String rstate;

    private String department;

}
