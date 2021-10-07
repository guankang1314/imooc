package com.imooc.service.center;

import java.util.List;

import com.imooc.pojo.OrderItems;
import com.imooc.pojo.bo.center.OrderItemsCommentBO;
import com.imooc.utils.PagedGridResult;

import io.swagger.models.auth.In;

/**
 * @author qingtian
 * @description:
 * @Package com.imooc.service.center
 * @date 2021/10/6 11:47
 */
public interface MyCommentsService {

    /**
     * 根据订单id查询关联的商品
     * @param orderId
     * @return
     */
    List<OrderItems> queryPendingComment(String orderId);

    /**
     * 保存用户的评论
     * @param orderId
     * @param userId
     * @param commentsList
     */
    void saveComments(String orderId,String userId,List<OrderItemsCommentBO> commentsList);

    /**
     * 查询自己的评论
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    PagedGridResult queryMyComments(String userId,Integer page, Integer pageSize);
}
