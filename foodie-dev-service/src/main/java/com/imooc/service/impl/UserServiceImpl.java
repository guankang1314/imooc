package com.imooc.service.impl;

import java.util.Date;

import org.apache.catalina.User;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.enums.Sex;
import com.imooc.mapper.UsersMapper;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBO;
import com.imooc.service.UserService;
import com.imooc.utils.DateUtil;
import com.imooc.utils.MD5Utils;

import tk.mybatis.mapper.entity.Example;

/**
 * @author qingtian
 * @description:
 * @Package com.imooc.service.impl
 * @date 2021/8/31 16:02
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private Sid sid;

    private static final String USER_FACE = "https://cdn.qingtianblog.com/pic/2021/08/09/91770118_p0_master1200.jpg";
    @Override
    public boolean queryUsernameIsExist(String username) {

        Example userExample = new Example(Users.class);
        userExample.createCriteria().andEqualTo("username",username);
        Users result = usersMapper.selectOneByExample(userExample);

        return result == null ? false : true;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users createUser(UserBO userBO) {

        String id = sid.nextShort();

        Users users = new Users();
        users.setId(id);
        users.setUsername(userBO.getUsername());
        try {
            users.setPassword(MD5Utils.getMD5Str(userBO.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //默认昵称相当于用户名
        users.setNickname(userBO.getUsername());
        users.setFace(USER_FACE);
        //设置默认的生日
        users.setBirthday(DateUtil.stringToDate("1902-01-02"));
        //设置默认的性别
        users.setSex(Sex.secret.type);
        users.setCreatedTime(new Date());
        users.setUpdatedTime(new Date());

        //保存user
        usersMapper.insert(users);
        return users;
    }
}
