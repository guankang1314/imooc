package com.imooc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBO;
import com.imooc.service.UserService;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.JsonUtils;
import com.imooc.utils.MD5Utils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author qingtian
 * @description:
 * @Package com.imooc.controller
 * @date 2021/8/31 16:09
 */
@Api(value = "注册登录",tags = {"用于注册登录的相关接口"})
@RestController
@RequestMapping("passport")
public class PassportController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户名是否存在",notes = "用户名是否存在",httpMethod = "GET")
    @GetMapping("/usernameIsExist")
    public IMOOCJSONResult usernameIsExist(@RequestParam String username) {

        if(StringUtils.isBlank(username)) {
            return IMOOCJSONResult.errorMsg("用户名不能为空");
        }

        //查找注册的用户名是否存在
        boolean isExist = userService.queryUsernameIsExist(username);

        if (isExist) {
            return IMOOCJSONResult.errorMsg("用户名已经存在");
        }
        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "用户注册",notes = "用户注册",httpMethod = "POST")
    @PostMapping("/regist")
    public IMOOCJSONResult regist(@RequestBody UserBO userBO,
                                    HttpServletRequest request,
                                    HttpServletResponse response) {

        //后端校验
        String username = userBO.getUsername();
        String password = userBO.getPassword();
        String confirmPassword = userBO.getConfirmPassword();

        //判断用户名和密码不能为空
        if (StringUtils.isBlank(username)
                || StringUtils.isBlank(password)
                || StringUtils.isBlank(confirmPassword)) {
            return IMOOCJSONResult.errorMsg("用户名或密码不能为空");
        }
        //判断用户名是否存在
        boolean isExist = userService.queryUsernameIsExist(username);

        if (isExist) {
            return IMOOCJSONResult.errorMsg("用户名已经存在");
        }
        //判断密码长度不能少于6位
        if (password.length() < 6) {
            return IMOOCJSONResult.errorMsg("密码长度不能小于6位");
        }
        //判断两次密码是否一致
        if (!StringUtils.equals(password,confirmPassword)) {
            return IMOOCJSONResult.errorMsg("两次输入的密码不一致");
        }
        //实现注册
        Users user = userService.createUser(userBO);
        CookieUtils.setCookie(request,response,"user", JsonUtils.objectToJson(user),true);
        return IMOOCJSONResult.ok(user);
    }

    @ApiOperation(value = "用户登录",notes = "用户登录",httpMethod = "POST")
    @PostMapping("/login")
    public IMOOCJSONResult login(@RequestBody UserBO userBO,
                                HttpServletRequest request,
                                HttpServletResponse response) throws Exception {

        //后端校验
        String username = userBO.getUsername();
        String password = userBO.getPassword();

        //判断用户名和密码不能为空
        if (StringUtils.isBlank(username)
                || StringUtils.isBlank(password)
                ) {
            return IMOOCJSONResult.errorMsg("用户名或密码不能为空");
        }

        //实现登录
        Users users = userService.queryUserForLogin(username, MD5Utils.getMD5Str(password));
        if (users == null) {
            return IMOOCJSONResult.errorMsg("用户名或密码不正确");
        }

        Users userResult = setNullProperty(users);

        CookieUtils.setCookie(request,response,"user", JsonUtils.objectToJson(userResult),true);

        // TODO 生成用户token，存入redis会话
        // TODO 同步购物车数据
        return IMOOCJSONResult.ok(userResult);
    }

    private Users setNullProperty(Users users) {
        users.setPassword(null);
        users.setMobile(null);
        users.setCreatedTime(null);
        users.setEmail(null);
        users.setBirthday(null);
        users.setUpdatedTime(null);
        return users;
    }

    @ApiOperation(value = "用户退出",notes = "用户退出",httpMethod = "POST")
    @PostMapping("/logout")
    public IMOOCJSONResult logout(@RequestParam String userId,
                                HttpServletRequest request,
                                HttpServletResponse response) {

        //清空用户有关cookie
        CookieUtils.deleteCookie(request,response,"user");

        // TODO 用户退出登录需要清空购物车

        //TODO 分布式会话中需要清空用户信息

        return IMOOCJSONResult.ok();
    }
}
