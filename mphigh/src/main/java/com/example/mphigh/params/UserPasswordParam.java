package com.example.mphigh.params;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserPasswordParam {
    @NotNull(message = "密码不能为空")
    private String oldpass;
    @NotNull(message = "新密码不能为空")
    private String pass;
    private String id;
}
