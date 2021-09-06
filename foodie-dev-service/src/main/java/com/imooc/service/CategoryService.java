package com.imooc.service;

import java.util.List;

import com.imooc.pojo.Category;
import com.imooc.pojo.vo.CategoryVO;

/**
 * @author qingtian
 * @description:
 * @Package com.imooc.service
 * @date 2021/9/6 15:39
 */
public interface CategoryService {

    /**
     * 查询所有一级分类
     * @return
     */
    List<Category> queryAllRootLevelCat();

    /**
     * 根据一级分类id查询子分类信息
     * @param rootCatId
     * @return
     */
    List<CategoryVO> getSubCatList(Integer rootCatId);
}
