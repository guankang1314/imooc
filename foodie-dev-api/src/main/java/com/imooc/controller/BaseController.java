package com.imooc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.imooc.pojo.Orders;
import com.imooc.service.center.MyOrderService;
import com.imooc.utils.IMOOCJSONResult;

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

    //用户上传头像的地址
    // public static final String IMAGE_USER_FACE_LOCATION = "D:\\images\\foodie\\faces";

    @Autowired
    public MyOrderService myOrderService;

    public IMOOCJSONResult checkUserOrders(String userId,String orderId) {
        Orders orders = myOrderService.queryMyOrders(userId, orderId);
        if (orders == null) {
            return IMOOCJSONResult.errorMsg("订单不存在");
        }
        return IMOOCJSONResult.ok(orders);
    }
}
