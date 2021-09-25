package com.imooc.controller;

import org.springframework.stereotype.Controller;

import io.swagger.models.auth.In;

/**
 * @author qingtian
 * @description:
 * @Package com.imooc.controller
 * @date 2021/9/8 16:23
 */
@Controller
public class BaseController {

    public static final String FOODIE_SHOPCART = "shopcart";

    public static final Integer COMMENT_PAGE_SIZE = 10;
    public static final Integer PAGE_SIZE = 20;

    //回调通知的url
    String payReturnUrl = "http://ip2gte.natappfree.cc/orders/notifyMerchantOrderPaid";

    //支付中心的地址
    String paymentUrl = "http://payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";
}
