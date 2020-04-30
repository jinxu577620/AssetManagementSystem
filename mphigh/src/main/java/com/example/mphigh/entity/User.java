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
    public User(){

    }
    
    public User(String uid, String uname, String cname, String department, String smauthority, String umauthority,
            String amauthority, String pauthority, String apauthority, String tel, String ustate, String email,
            String password) {
        this.uid = uid;
        this.uname = uname;
        this.cname = cname;
        this.department = department;
        this.smauthority = smauthority;
        this.umauthority = umauthority;
        this.amauthority = amauthority;
        this.pauthority = pauthority;
        this.apauthority = apauthority;
        this.tel = tel;
        this.ustate = ustate;
        this.email = email;
        this.password = password;
    }

}
