package com.example.mphigh.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.mphigh.entity.User;
import com.example.mphigh.enums.UserStatus;
import com.example.mphigh.mapper.UserMapper;
import com.example.mphigh.result.CodeMsg;
import com.example.mphigh.result.Result;
import com.example.mphigh.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mphigh.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jinxu18829190342@163.com
 * @since 2020-04-26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public Result login(String tel, String password) {
        User user = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUid, tel));
        
        if (user == null){
            return Result.error(CodeMsg.NO_USER);
        }
        System.out.println(user.getPassword());
        System.out.println(user.toString());
        if (!user.getPassword().equals(password)){
            return Result.error(CodeMsg.PASSWORD_ERROR);
        }
        if (!user.getUstate().equals(UserStatus.正常.name())){
            return Result.error(CodeMsg.FREEZE_OR_DELETED);
        }

        // todo 创建登录日志

        // 使用token，就不需要在session中存user了
        return Result.success(TokenUtil.getToken(user.getUid()), user);
    }
}
