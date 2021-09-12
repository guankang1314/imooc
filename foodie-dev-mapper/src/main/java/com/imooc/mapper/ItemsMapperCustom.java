package com.imooc.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.imooc.my.mapper.MyMapper;
import com.imooc.pojo.Items;
import com.imooc.pojo.vo.ItemCommentVO;
import com.imooc.pojo.vo.SearchItemsVO;
import com.imooc.pojo.vo.ShopcartVO;

public interface ItemsMapperCustom {

    List<ItemCommentVO> queryItemComments(@Param("paramsMap") Map<String,Object> map);

    List<SearchItemsVO> searchItems(@Param("paramsMap") Map<String,Object> map);

    List<SearchItemsVO> searchItemsByThirdCat(@Param("paramsMap") Map<String,Object> map);

    List<ShopcartVO> queryItemsBySpecIds(@Param("paramsList") List specIds);
}