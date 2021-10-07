package com.imooc.mapper;

import java.util.List;
import java.util.Map;

import javax.validation.OverridesAttribute;

import org.apache.ibatis.annotations.Param;

import com.imooc.pojo.vo.MyCommentVO;

/**
 * @author qingtian
 * @description:
 * @Package com.imooc.mapper
 * @date 2021/10/6 15:22
 */
public interface ItemsCommentsMapperCustom {

    void saveComments(Map<String,Object> map);

    List<MyCommentVO> queryMyComments(@Param("paramsMap") Map<String,Object> map);
}
