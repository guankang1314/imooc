package com.imooc.pojo.vo;

import lombok.Data;

/**
 * @author qingtian
 * @description:
 * @Package com.imooc.pojo.vo
 * @date 2021/9/11 12:05
 */
@Data
public class SearchItemsVO {


    private String itemId;
    private String itemName;
    private int sellCounts;
    private String imgUrl;
    private int price;

}
