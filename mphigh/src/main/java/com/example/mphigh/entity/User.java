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
public class User  {

    private static final long serialVersionUID = 1L;

    @TableId(value = "uid", type = IdType.NONE)
    private String uid;

    private String uname;

    private String cname;

    private String department;

    private String smauthority;

    private String umauthority;

    private String amauthority;

    private String pauthority;

    private String apauthority;

    private String tel;

    private String ustate;

    private String email;

    private String password;

}
