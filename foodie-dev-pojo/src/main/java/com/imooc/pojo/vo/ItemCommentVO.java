package com.imooc.pojo.vo;

import java.util.Date;

import lombok.Data;

/**
 * @author qingtian
 * @description:
 * @Package com.imooc.pojo.vo
 * @date 2021/9/8 14:11
 */
@Data
public class ItemCommentVO {

    private Integer commentLevel;
    private String content;
    private String specName;
    private Date createdTime;
    private String face;
    private String nickname;

}
