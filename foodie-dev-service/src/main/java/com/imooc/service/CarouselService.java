package com.imooc.service;

import java.util.List;

import com.imooc.pojo.Carousel;

/**
 * @author qingtian
 * @description:
 * @Package com.imooc.service
 * @date 2021/9/6 13:39
 */
public interface CarouselService {

    /**
     * 查询所有轮播图列表
     * @param isShow
     * @return
     */
    List<Carousel> queryAll(Integer isShow);
}
