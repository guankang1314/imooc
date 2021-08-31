package com.imooc.service;

import com.imooc.pojo.Stu;

/**
 * @author qingtian
 * @description:
 * @Package com.imooc.service
 * @date 2021/8/30 16:38
 */
public interface StuService {

    Stu getStuInfo(int id);

    void saveStu();

    void uodateStu(int id);

    void deleteStu(int id);
}
