package com.imooc.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.imooc.pojo.OrderStatus;
import com.imooc.pojo.Orders;
import com.imooc.pojo.vo.MyOrdersVO;

/**
 * @author qingtian
 * @description:
 * @Package com.imooc.mapper
 * @date 2021/10/3 21:53
 */
public interface OrdersMapperCustom {

    List<MyOrdersVO> queryMyOrders(@Param("paramsMap")Map<String,Object> map);

    int getMyOrderStatusCounts(@Param("paramsMap")Map<String,Object> map);

    List<OrderStatus> getMyOrderTrend(@Param("paramsMap")Map<String,Object> map);
}
