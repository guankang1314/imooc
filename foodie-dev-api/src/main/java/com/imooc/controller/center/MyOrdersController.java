package com.imooc.controller.center;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.imooc.controller.BaseController;
import com.imooc.pojo.Orders;
import com.imooc.pojo.vo.OrderStatusCountsVO;
import com.imooc.service.center.MyOrderService;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.PagedGridResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author qingtian
 * @description:
 * @Package com.imooc.controller.center
 * @date 2021/10/4 10:11
 */
@Api(value = "用户中心订单查询" ,tags = {"用户中心订单查询接口"})
@RestController
@RequestMapping("myorders")
public class MyOrdersController extends BaseController {

    // @Autowired
    // private MyOrderService myOrderService;

    @PostMapping("/query")
    public IMOOCJSONResult comments(@RequestParam String userId,
                                    @RequestParam Integer orderStatus,
                                    @RequestParam Integer page,
                                    @RequestParam Integer pageSize) {
        if (StringUtils.isBlank(userId)) {
            return IMOOCJSONResult.errorMsg(null);
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = COMMENT_PAGE_SIZE;
        }

        PagedGridResult result = myOrderService.queryMyOrders(userId, orderStatus, page, pageSize);
        return IMOOCJSONResult.ok(result);
    }

    @ApiOperation(value = "商家发货",notes = "商家发货",httpMethod = "GET")
    @GetMapping("/deliver")
    public IMOOCJSONResult deliver(
        @ApiParam(name = "orderId",value = "订单id",required = true)
        @RequestParam String orderId) {
        if (StringUtils.isBlank(orderId)) {
            return IMOOCJSONResult.errorMsg("订单id不能为空");
        }
        myOrderService.updateDeliverOrderStatus(orderId);
        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "用户确认收货",notes = "用户确认收货",httpMethod = "POST")
    @PostMapping("/confirmReceive")
    public IMOOCJSONResult confirmReceive(
            @ApiParam(name = "orderId",value = "订单id",required = true)
            @RequestParam String orderId,
            @ApiParam(name = "userId",value = "用户id",required = true)
            @RequestParam String userId) {
        IMOOCJSONResult result = checkUserOrders(userId, orderId);
        if (!result.getStatus().equals(HttpStatus.OK.value())) {
            return result;
        }

        boolean res = myOrderService.updateReceiveOrders(orderId);
        if (!res) {
            return IMOOCJSONResult.errorMsg("订单确认收货失败！");
        }
        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "用户删除订单",notes = "用户删除订单",httpMethod = "POST")
    @PostMapping("/delete")
    public IMOOCJSONResult delete(
            @ApiParam(name = "orderId",value = "订单id",required = true)
            @RequestParam String orderId,
            @ApiParam(name = "userId",value = "用户id",required = true)
            @RequestParam String userId) {
        IMOOCJSONResult result = checkUserOrders(userId, orderId);
        if (!result.getStatus().equals(HttpStatus.OK.value())) {
            return result;
        }

        boolean res = myOrderService.deleteOrders(userId,orderId);
        if (!res) {
            return IMOOCJSONResult.errorMsg("订单删除失败！");
        }
        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "获得订单状态数概况",notes = "获得订单状态数概况",httpMethod = "POST")
    @PostMapping("/statusCounts")
    public IMOOCJSONResult statusCounts(
            @ApiParam(name = "userId",value = "用户id",required = true)
            @RequestParam String userId) {
        if (StringUtils.isBlank(userId)) {
            return IMOOCJSONResult.errorMsg(null);
        }

        OrderStatusCountsVO statusCounts = myOrderService.getOrderStatusCounts(userId);

        return IMOOCJSONResult.ok(statusCounts);
    }


    @PostMapping("/trend")
    public IMOOCJSONResult trend(@RequestParam String userId,
            @RequestParam Integer page,
            @RequestParam Integer pageSize) {
        if (StringUtils.isBlank(userId)) {
            return IMOOCJSONResult.errorMsg(null);
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = COMMENT_PAGE_SIZE;
        }

        PagedGridResult result = myOrderService.getOrdersTrend(userId, page, pageSize);
        return IMOOCJSONResult.ok(result);
    }

    // private IMOOCJSONResult checkUserOrders(String userId,String orderId) {
    //     Orders orders = myOrderService.queryMyOrders(userId, orderId);
    //     if (orders == null) {
    //         return IMOOCJSONResult.errorMsg("订单不存在");
    //     }
    //     return IMOOCJSONResult.ok();
    // }
}
