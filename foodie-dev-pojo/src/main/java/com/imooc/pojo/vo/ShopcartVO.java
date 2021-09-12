package com.imooc.pojo.vo;

import lombok.Data;

/**
 * @author qingtian
 * @description:
 * @Package com.imooc.pojo.bo
 * @date 2021/9/11 16:50
 */
@Data
public class ShopcartVO {

    private String itemId;
    private String itemImgUrl;
    private String itemName;
    private String specId;
    private String specName;
    private String priceDiscount;
    private String priceNormal;

}
