package com.example.mphigh.params;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class UserParam {
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

    private String email;

    private String password;
}
