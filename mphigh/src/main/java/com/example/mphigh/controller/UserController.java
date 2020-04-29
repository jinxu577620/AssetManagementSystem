package com.example.mphigh.controller;


import com.example.mphigh.entity.User;
import com.example.mphigh.params.UserPasswordParam;
import com.example.mphigh.result.CodeMsg;
import com.example.mphigh.result.Result;
import com.example.mphigh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jinxu18829190342@163.com
 * @since 2020-04-26
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    
    @PostMapping(value = "/login")
    public Result login(@NotNull(message = "账号不能为空") String tel,
                        @NotNull(message="密码不能为空") String password, HttpServletRequest request) {
        System.out.println(tel);
        System.out.println(password);
        return userService.login(tel, password);
    }
    @RequestMapping(value = "/logout", method = { RequestMethod.GET, RequestMethod.POST })
    public Result logout(
//			@RequestAttribute("userId") String userId,
            HttpServletRequest request) {
//		User user = userService.getById(userId);
//		log.info(user.getName()+"退出登录");
//		HttpSession session = request.getSession();
//		session.removeAttribute("imgCode");
//		session.removeAttribute("telCode");

        return Result.success();
    }
    @PostMapping(value = "/updatePassword")
    public Result saveUser(@Valid UserPasswordParam userForm, HttpServletRequest request) {
        //原密码 d2840ffc95fd8a5c5c0fb53fc5ff8ea9
        System.out.println("tel:"+userForm.getId()+", 新的password:"+userForm.getPass());
        User operateUser = userService.getById(userForm.getId());
        if (!operateUser.getPassword().equals(userForm.getOldpass())){
            return Result.error(CodeMsg.PASSWORD_ERROR);
        }
        operateUser.setPassword(userForm.getPass());
        userService.updateById(operateUser);
        System.out.println(operateUser.toString());
        return Result.success();
    }
}
