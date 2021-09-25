package com.imooc.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.catalina.User;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.enums.YesOrNo;
import com.imooc.mapper.UserAddressMapper;
import com.imooc.pojo.UserAddress;
import com.imooc.pojo.bo.AddressBO;
import com.imooc.service.AddressService;

/**
 * @author qingtian
 * @description:
 * @Package com.imooc.service.impl
 * @date 2021/9/12 19:14
 */
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private UserAddressMapper userAddressMapper;

    @Autowired
    private Sid sid;

    @Override
    public List<UserAddress> queryAll(String userId) {

        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        return userAddressMapper.select(userAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void addNewUserAddress(AddressBO addressBO) {

        Integer isDefault = 0;

        String addressId = sid.nextShort();
        //判断用户是否存在地址，如果没有，则新增为默认地址
        List<UserAddress> list = this.queryAll(addressBO.getUserId());
        if (list == null || list.isEmpty() || list.size() == 0) {
            isDefault = 1;
        }
        //保存地址到数据库
        UserAddress userAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO,userAddress);
        userAddress.setId(addressId);
        userAddress.setIsDefault(isDefault);
        userAddress.setCreatedTime(new Date());
        userAddress.setUpdatedTime(new Date());
        userAddressMapper.insert(userAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUserAddress(AddressBO addressBO) {

        String addressId = addressBO.getAddressId();

        UserAddress updateAdress = new UserAddress();
        BeanUtils.copyProperties(addressBO,updateAdress);

        updateAdress.setId(addressId);
        updateAdress.setUpdatedTime(new Date());
        userAddressMapper.updateByPrimaryKeySelective(updateAdress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteUserAddress(String userId, String addressId) {

        UserAddress userAddress = new UserAddress();
        userAddress.setId(addressId);
        userAddress.setUserId(userId);
        userAddressMapper.delete(userAddress);
    }


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUserAddressToBeDefault(String userId, String addressId) {

        UserAddress queryAddress = new UserAddress();
        queryAddress.setUserId(userId);
        queryAddress.setIsDefault(YesOrNo.YES.type);
        List<UserAddress> list = userAddressMapper.select(queryAddress);
        for (UserAddress userAddress : list) {
            userAddress.setIsDefault(YesOrNo.NO.type);
            userAddressMapper.updateByPrimaryKeySelective(userAddress);
        }

        UserAddress defaultAddress = new UserAddress();
        defaultAddress.setUserId(userId);
        defaultAddress.setId(addressId);
        defaultAddress.setIsDefault(YesOrNo.YES.type);
        userAddressMapper.updateByPrimaryKeySelective(defaultAddress);
    }

    @Override
    public UserAddress queryUserAddress(String userId, String addressId) {

        UserAddress singleAddress = new UserAddress();
        singleAddress.setUserId(userId);
        singleAddress.setId(addressId);

        return userAddressMapper.selectOne(singleAddress);
    }

}
