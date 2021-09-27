package com.imooc.service.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.internal.engine.resolver.JPATraversableResolver;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.YesOrNo;
import com.imooc.mapper.OrderItemsMapper;
import com.imooc.mapper.OrderStatusMapper;
import com.imooc.mapper.OrdersMapper;
import com.imooc.pojo.Items;
import com.imooc.pojo.ItemsSpec;
import com.imooc.pojo.OrderItems;
import com.imooc.pojo.OrderStatus;
import com.imooc.pojo.Orders;
import com.imooc.pojo.UserAddress;
import com.imooc.pojo.bo.SubmitOrderBO;
import com.imooc.pojo.vo.MerchantOrdersVO;
import com.imooc.pojo.vo.OrderVO;
import com.imooc.service.AddressService;
import com.imooc.service.ItemService;
import com.imooc.service.OrderService;
import com.imooc.utils.DateUtil;

import tk.mybatis.mapper.annotation.Order;

/**
 * @author qingtian
 * @description:
 * @Package com.imooc.service.impl
 * @date 2021/9/15 23:11
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private Sid sid;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private ItemService itemService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private OrderItemsMapper orderItemsMapper;

    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public OrderVO createOrder(SubmitOrderBO submitOrderBO) {
        String userId = submitOrderBO.getUserId();
        String addressId = submitOrderBO.getAddressId();
        String itemSpecIds = submitOrderBO.getItemSpecIds();
        Integer payMethod = submitOrderBO.getPayMethod();
        String leftMsg = submitOrderBO.getLeftMsg();
        //包邮费用设置为0
        Integer postAmount = 0;
        String orderId = sid.nextShort();

        //查询用户地址
        UserAddress userAddress = addressService.queryUserAddress(userId, addressId);
        Orders orders = new Orders();
        orders.setId(orderId);
        orders.setUserId(userId);
        orders.setReceiverName(userAddress.getReceiver());
        orders.setReceiverMobile(userAddress.getMobile());
        orders.setReceiverAddress(userAddress.getProvince()+" "
                +userAddress.getCity()+ " "
                +userAddress.getDistrict()+" "+userAddress.getDetail());
        orders.setPostAmount(postAmount);
        orders.setPayMethod(payMethod);
        orders.setLeftMsg(leftMsg);
        orders.setIsComment(YesOrNo.NO.type);
        orders.setIsDelete(YesOrNo.NO.type);
        orders.setCreatedTime(new Date());
        orders.setUpdatedTime(new Date());

        //循环根据itemSpecIds保存订单商品信息表
        String[] itemSpecIdArr = itemSpecIds.split(",");
        Integer totalAmount = 0;
        Integer realPatAmount = 0;
        for (String itemSpec : itemSpecIdArr) {
            // TODO 整合redis后，商品购买的数量重新从redis中获取
            int buyCount = 1;

            //根据规格id，查询规格的具体信息，主要获取价格
            ItemsSpec itemsSpec = itemService.queryItemSpecById(itemSpec);
            totalAmount += itemsSpec.getPriceNormal() * buyCount;
            realPatAmount += itemsSpec.getPriceDiscount() * buyCount;

            //根据itemId查询商品信息以及图片
            String itemId = itemsSpec.getItemId();
            Items item = itemService.queryItemById(itemId);
            String url = itemService.queryItemMainImgById(itemId);

            //循环保存子订单到数据库
            String subOrderId = sid.nextShort();
            OrderItems orderItems = new OrderItems();
            orderItems.setId(subOrderId);
            orderItems.setOrderId(orderId);
            orderItems.setItemId(itemId);
            orderItems.setItemImg(url);
            orderItems.setItemName(item.getItemName());
            orderItems.setBuyCounts(buyCount);
            orderItems.setItemSpecId(itemSpec);
            orderItems.setItemSpecName(itemsSpec.getName());
            orderItems.setPrice(itemsSpec.getPriceDiscount());
            orderItemsMapper.insert(orderItems);

            //在用户创建订单后，需要减库存
            itemService.decreaseItemSpecStock(itemSpec,buyCount);
        }
        orders.setTotalAmount(totalAmount);
        orders.setRealPayAmount(realPatAmount);
        ordersMapper.insert(orders);

        //保存订单状态表
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        orderStatus.setCreatedTime(new Date());
        orderStatusMapper.insert(orderStatus);

        //构建商户订单，用于传给支付中心
        MerchantOrdersVO merchantOrdersVO = new MerchantOrdersVO();
        merchantOrdersVO.setMerchantOrderId(orderId);
        merchantOrdersVO.setMerchantUserId(userId);
        merchantOrdersVO.setAmount(realPatAmount + postAmount);
        merchantOrdersVO.setPayMethod(payMethod);

        //构建自定义VO
        OrderVO orderVO = new OrderVO();
        orderVO.setOrderId(orderId);
        orderVO.setMerchantOrdersVO(merchantOrdersVO);

        return orderVO;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateOrderStatus(String orderId, Integer orderStatus) {
        OrderStatus paidStatus = new OrderStatus();
        paidStatus.setOrderId(orderId);
        paidStatus.setOrderStatus(orderStatus);
        paidStatus.setPayTime(new Date());

        orderStatusMapper.updateByPrimaryKeySelective(paidStatus);
    }

    @Override
    public OrderStatus queryOrderStatusInfo(String orderId) {

        return orderStatusMapper.selectByPrimaryKey(orderId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void closeOrder() {
        //查询所有未支付订单，判断时间是否超时（1天），超时则关闭订单
        OrderStatus queryOrder = new OrderStatus();
        queryOrder.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        List<OrderStatus> list = orderStatusMapper.select(queryOrder);
        list.stream().forEach(orderStatus ->
                {
                    //获得订单创建时间
                    Date createdTime = orderStatus.getCreatedTime();
                    int days = DateUtil.daysBetween(createdTime, new Date());
                    if (days >= 1) {
                        doCloseOrder(orderStatus.getOrderId());
                    }
                }
        );
    }

    @Transactional(propagation = Propagation.REQUIRED)
    void doCloseOrder(String orderId) {
        OrderStatus close = new OrderStatus();
        close.setOrderId(orderId);
        close.setOrderStatus(OrderStatusEnum.CLOSE.type);
        close.setCloseTime(new Date());
        orderStatusMapper.updateByPrimaryKeySelective(close);
    }
}
