package com.example.mphigh.controller;


import cn.hutool.core.util.StrUtil;
import com.example.mphigh.entity.User;
import com.example.mphigh.params.UserParam;
import com.example.mphigh.params.UserPasswordParam;
import com.example.mphigh.result.CodeMsg;
import com.example.mphigh.result.Result;
import com.example.mphigh.service.UserService;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping(value = "/getUsers")
    public Result getUsers(
            String uid,
            @RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        return userService.findByid(uid, pageIndex, pageSize);
    }
    @DeleteMapping(value = "/deleteUser")
    public Result deleteUser(@NotNull(message = "Id不能为空") @Length(min=32, max=32) String uid){
        User user = userService.getById(uid);
        if (user == null){
            return Result.error(CodeMsg.NO_USER);
        }
        userService.delete(user);
        return Result.success();
    }
    @PostMapping(value = "/saveUser")
    public Result saveUser(@Valid UserParam userForm, HttpServletRequest request) {
        System.out.println("工号:"+userForm.getUid()+", password:"+userForm.getPassword());
        System.out.println(userForm.getUid());
        User operateUser = userService.getById(userForm.getUid());
        System.out.println(operateUser.toString());
        System.out.println("进来了吗");
        // id不为空，则修改
        if (StrUtil.isNotBlank(userForm.getUid())){
            return userService.updateUser(userForm);
        }
        return Result.success();
    }
    @PutMapping(value = "/freezeUser")
    public Result freezeUser(@NotNull(message = "Id不能为空") @Length(min=32, max=32) String uid){
        User user = userService.getById(uid);
        if (user == null){
            return Result.error(CodeMsg.NO_USER);
        }
        userService.lambdaUpdate().eq(User::getUid, uid).set(User::getUstate, "冻结").update();
        user.setUstate("冻结");
        return Result.success(user);
    }
    @PutMapping(value = "/unFreezeUser")
    public Result unFreezeUser(@NotNull(message = "Id不能为空") @Length(min=32, max=32) String uid){
        User user = userService.getById(uid);
        if (user == null){
            return Result.error(CodeMsg.NO_USER);
        }
        userService.lambdaUpdate().eq(User::getUid, uid).set(User::getUstate, "正常").update();
        user.setUstate("正常");
        return Result.success(user);
    }
}
