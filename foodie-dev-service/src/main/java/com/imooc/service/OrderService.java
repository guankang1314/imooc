package com.imooc.service;

import com.imooc.pojo.OrderStatus;
import com.imooc.pojo.bo.SubmitOrderBO;
import com.imooc.pojo.vo.OrderVO;

/**
 * @author qingtian
 * @description:
 * @Package com.imooc.service
 * @date 2021/9/15 23:11
 */
public interface OrderService {

    /**
     * 用户创建订单相关信息
     * @param submitOrderBO
     */
    OrderVO createOrder(SubmitOrderBO submitOrderBO);

    /**
     * 修改订单状态
     * @param orderId
     * @param orderStatus
     */
    void updateOrderStatus(String orderId,Integer orderStatus);

    /**
     *查询订单状态
     * @param orderId
     */
    OrderStatus queryOrderStatusInfo(String orderId);

    /**
     * 关闭超时未支付订单
     */
    void closeOrder();
}
