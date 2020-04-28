package com.example.mphigh.listener;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.mphigh.entity.User;
import com.example.mphigh.enums.UserStatus;
import com.example.mphigh.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener  //使用注解的方式声明Listener时，需要再main函数类上添加@ServletComponentScan
@Slf4j
public class ContextListener implements ServletContextListener {

    @Autowired
    private UserService userService;

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        log.info("liting: contextDestroyed");
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        log.info("Listening: contextInitialized");

        User admin = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUid, "15159123141"));
        System.out.println("没找到");
        if (admin == null){
            log.info("初始化超级系统管理员");
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
