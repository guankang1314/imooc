package com.imooc.pojo.vo;

import lombok.Data;

/**
 * @author qingtian
 * @description:
 * @Package com.imooc.pojo.vo
 * @date 2021/9/21 17:29
 */
@Data
public class OrderVO {

    private String orderId;
    private MerchantOrdersVO merchantOrdersVO;
}
