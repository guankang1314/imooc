package com.imooc.service;

import java.util.List;

import com.imooc.pojo.Items;
import com.imooc.pojo.ItemsImg;
import com.imooc.pojo.ItemsParam;
import com.imooc.pojo.ItemsSpec;
import com.imooc.pojo.vo.CommentLevelCountsVO;
import com.imooc.pojo.vo.ItemCommentVO;
import com.imooc.pojo.vo.ShopcartVO;
import com.imooc.utils.PagedGridResult;

/**
 * @author qingtian
 * @description:
 * @Package com.imooc.service
 * @date 2021/9/7 14:25
 */
public interface ItemService {

    /**
     * 根据商品id查询详情
     * @param itemId
     * @return
     */
    Items queryItemById(String itemId);

    /**
     * 根据商品id查询商品图片列表
     * @param itemId
     * @return
     */
    List<ItemsImg> queryItemImgList(String itemId);

    /**
     * 查询商品规格
     * @param itemId
     * @return
     */
    List<ItemsSpec> queryItemsSpecList(String itemId);

    /**
     * 根据商品id查询商品参数
     * @param itemId
     * @return
     */
    ItemsParam queryItemsParam(String itemId);

    /**
     * 根据商品id查询商品的评价等级数量
     * @param itemId
     * @return
     */
    CommentLevelCountsVO queryCommentCounts(String itemId);

    /**
     * 根据商品od查询商品的评价（分页）
     * @param itemId
     * @param level
     * @return
     */
    PagedGridResult queryPagedComments(String itemId,Integer level,Integer page,Integer pageSize);

    /**
     * 搜索商品列表
     * @param keywords
     * @param sort
     * @param page
     * @param pageSize
     * @return
     */
    PagedGridResult searchItems(String keywords,String sort,Integer page,Integer pageSize);


    /**
     * 根据三级分类id查询商品列表
     * @param catId
     * @param sort
     * @param page
     * @param pageSize
     * @return
     */
    PagedGridResult searchItems(int catId,String sort,Integer page,Integer pageSize);

    /**
     * 根据规格ids查询最新的购物车中商品数据
     * @param specIds
     * @return
     */
    List<ShopcartVO> queryItemsBySpecIds(String specIds);

    /**
     * 根据specId查询商品规格数据
     * @param specId
     * @return
     */
    ItemsSpec queryItemSpecById(String specId);

    /**
     * 根据商品id获取主图
     * @param itemId
     * @return
     */
    String queryItemMainImgById(String itemId);

    /**
     * 减少库存
     * @param specId
     * @param buyCounts
     */
    void decreaseItemSpecStock(String specId,int buyCounts);
}
