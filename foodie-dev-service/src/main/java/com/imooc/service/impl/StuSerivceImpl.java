package com.imooc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.mapper.StuMapper;
import com.imooc.pojo.Stu;
import com.imooc.service.StuService;

/**
 * @author qingtian
 * @description:
 * @Package com.imooc.service.impl
 * @date 2021/8/30 16:40
 */
@Service
public class StuSerivceImpl implements StuService {

    @Autowired
    private StuMapper stuMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Stu getStuInfo(int id) {

        Stu stu = stuMapper.selectByPrimaryKey(id);
        return stu;
    }

    @Override
    public void saveStu() {

    }

    @Override
    public void uodateStu(int id) {

    }

    @Override
    public void deleteStu(int id) {

    }
}
