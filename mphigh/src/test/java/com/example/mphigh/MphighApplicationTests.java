package com.example.mphigh;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.mphigh.entity.User;
import com.example.mphigh.mapper.UserMapper;
import com.example.mphigh.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
@SpringBootTest
class MphighApplicationTests {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @Test
    public void contextLoads() {
//        System.out.println(("----- selectAll method test ------"));
//        List<User> userList = userMapper.selectList(null);
//        userList.forEach(System.out::println);
        User admin = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUid, "15159123141"));
        System.out.println("没找到");
        if (admin == null){
            admin = new User();
            admin.setUid("15159123141");
            admin.setUname("神魔玩意啊");
            admin.setDepartment("学生会");
            admin.setCname("超级管理员");
            try {
                admin.setPassword(SecureUtil.md5("qtmzgadmin"));
            } catch (Exception e) {
                admin.setPassword("e10adc3949ba59abbe56e057f20f883e");
            }
            userService.save(admin);
        }

    }

}
