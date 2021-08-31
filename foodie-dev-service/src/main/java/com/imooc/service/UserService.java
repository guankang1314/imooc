package com.imooc.service;

import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBO;

/**
 * @author qingtian
 * @description:
 * @Package com.imooc.service
 * @date 2021/8/31 16:00
 */
public interface UserService {

    /**
     * 判断用户名是否存在
     */
    boolean queryUsernameIsExist(String username);

    /**
     * 创建用户
     * @param userBO
     * @return
     */
    Users createUser(UserBO userBO);
}
