package com.imooc.service;

import java.util.List;

import com.imooc.pojo.UserAddress;
import com.imooc.pojo.bo.AddressBO;

/**
 * @author qingtian
 * @description:
 * @Package com.imooc.service
 * @date 2021/9/12 19:14
 */
public interface AddressService {

    /**
     * 根据用户的id查询用户的地址列表
     * @param userId
     * @return
     */
    List<UserAddress> queryAll(String userId);

    /**
     * 用户新增地址
     * @param addressBO
     */
    void addNewUserAddress(AddressBO addressBO);
}
