package com.imooc.controller.center;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.imooc.controller.BaseController;
import com.imooc.enums.YesOrNo;
import com.imooc.pojo.OrderItems;
import com.imooc.pojo.Orders;
import com.imooc.pojo.bo.center.OrderItemsCommentBO;
import com.imooc.service.center.MyCommentsService;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.PagedGridResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author qingtian
 * @description:
 * @Package com.imooc.controller.center
 * @date 2021/10/6 12:57
 */
@Api(value = "用户中心评价模块",tags = {"用户中心评价模块相关接口"})
@RestController
@RequestMapping("mycomments")
public class MyCommentsController extends BaseController {

    @Autowired
    private MyCommentsService myCommentsService;

    @PostMapping("/pending")
    public IMOOCJSONResult pending(@RequestParam String userId,
                                    @RequestParam String orderId) {
        IMOOCJSONResult result = checkUserOrders(userId, orderId);
        if (result.getStatus() != HttpStatus.OK.value()) {
            return result;
        }
        Orders orders = (Orders) result.getData();
        if (orders.getIsComment().equals(YesOrNo.YES.type)) {
            return IMOOCJSONResult.errorMsg("该笔订单以及评价过。");
        }

        List<OrderItems> list = myCommentsService.queryPendingComment(orderId);

        return IMOOCJSONResult.ok(list);
    }

    @PostMapping("/saveList")
    public IMOOCJSONResult saveList(@RequestParam String userId,
                                    @RequestParam String orderId,
                                    @RequestBody List<OrderItemsCommentBO> commentList) {

        System.out.println(commentList);
        IMOOCJSONResult result = checkUserOrders(userId, orderId);
        if (result.getStatus() != HttpStatus.OK.value()) {
            return result;
        }
        //判断评论内容不为空
        if (CollectionUtils.isEmpty(commentList)) {
            return IMOOCJSONResult.errorMsg("评论内容不能为空!");
        }

        myCommentsService.saveComments(orderId,userId,commentList);
        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "查询我的评价",notes = "查询我的评价",httpMethod = "POST")
    @PostMapping("/query")
    public IMOOCJSONResult query(@RequestParam String userId,
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

        PagedGridResult result = myCommentsService.queryMyComments(userId, page, pageSize);
        return IMOOCJSONResult.ok(result);
    }
}
