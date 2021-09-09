package com.imooc.pojo.vo;

import java.util.List;

import com.imooc.pojo.Items;
import com.imooc.pojo.ItemsImg;
import com.imooc.pojo.ItemsParam;
import com.imooc.pojo.ItemsSpec;

import lombok.Data;

/**
 * @author qingtian
 * @description:
 * @Package com.imooc.pojo.vo
 * @date 2021/9/7 20:11
 */
@Data
public class ItemInfoVO {

    private Items item;
    private List<ItemsImg> itemImgList;
    private List<ItemsSpec> itemSpecList;
    private ItemsParam itemParams;


}
