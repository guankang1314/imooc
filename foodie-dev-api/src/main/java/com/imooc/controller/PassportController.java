package com.imooc.controller;

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
import com.imooc.utils.IMOOCJSONResult;

/**
 * @author qingtian
 * @description:
 * @Package com.imooc.controller
 * @date 2021/8/31 16:09
 */
@RestController
@RequestMapping("passport")
public class PassportController {

    @Autowired
    private UserService userService;

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

    @PostMapping("/regist")
    public IMOOCJSONResult regist(@RequestBody UserBO userBO) {

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
        return IMOOCJSONResult.ok(user);
    }
}
