package com.example.mphigh.service;

import com.example.mphigh.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mphigh.result.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jinxu18829190342@163.com
 * @since 2020-04-26
 */
public interface UserService extends IService<User> {

    Result login(String tel, String password);
}
