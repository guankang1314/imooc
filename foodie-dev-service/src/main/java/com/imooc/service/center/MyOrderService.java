package com.imooc.service.center;

import com.imooc.pojo.Orders;
import com.imooc.pojo.vo.OrderStatusCountsVO;
import com.imooc.utils.PagedGridResult;

/**
 * @author qingtian
 * @description:
 * @Package com.imooc.service.center
 * @date 2021/10/4 9:21
 */
public interface MyOrderService {

    /**
     * 查询我的订单列表
     * @param userId
     * @param orderStatus
     * @param page
     * @param pageSize
     * @return
     */
    PagedGridResult queryMyOrders(String userId,Integer orderStatus,Integer page,Integer pageSize);

    /**
     * 将订单状态改变 订单状态 ——> 商家发货
     * @param orderId
     */
    void updateDeliverOrderStatus(String orderId);

    /**
     * 查询我的订单记录
     * @param userId
     * @param orderId
     * @return
     */
    Orders queryMyOrders(String userId,String orderId);

    /**
     * 更新订单状态 ——> 确认收货
     * @param orderId
     * @return
     */
    boolean updateReceiveOrders(String orderId);

    /**
     * 用户删除订单
     * @param userId
     * @param orderId
     * @return
     */
    boolean deleteOrders(String userId,String orderId);

    /**
     * 查询用户订单数
     * @param userId
     */
    OrderStatusCountsVO getOrderStatusCounts(String userId);

    /**
     * 查询用户订单的动向
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    PagedGridResult getOrdersTrend(String userId,Integer page,Integer pageSize);
}
