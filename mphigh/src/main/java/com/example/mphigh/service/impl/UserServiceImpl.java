package com.example.mphigh.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mphigh.entity.User;
import com.example.mphigh.enums.UserStatus;
import com.example.mphigh.mapper.UserMapper;
import com.example.mphigh.params.UserParam;
import com.example.mphigh.result.CodeMsg;
import com.example.mphigh.result.Result;
import com.example.mphigh.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mphigh.utils.TokenUtil;
import org.springframework.beans.BeanUtils;
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

    @Override
    public void delete(User user) {
            userMapper.delete(user);
    }

    @Override
    public Result findByid(String uid, Integer pageIndex, Integer pageSize) {
        //设置当前页和页容量
        Page<User> page = new Page<>(pageIndex, pageSize);

        IPage<User> userIPage = userMapper.selectPage(page, Wrappers.<User>lambdaQuery().like(StringUtils.isNotBlank(uid), User::getUid, uid));

        return Result.success(userIPage);
    }

    @Override
    public Result updateUser(UserParam userParam) {
        User user = userMapper.selectById(userParam.getUid());
        BeanUtils.copyProperties(userParam, user);
        userMapper.updateById(user);
        return Result.success(user);
    }
}
