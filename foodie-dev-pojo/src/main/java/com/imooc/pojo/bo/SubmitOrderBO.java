package com.imooc.pojo.bo;

import lombok.Data;

/**
 * @author qingtian
 * @description:
 * @Package com.imooc.pojo.bo
 * @date 2021/9/14 23:16
 */
@Data
public class SubmitOrderBO {

    private String userId;
    private String itemSpecIds;
    private String addressId;
    private Integer payMethod;
    private String leftMsg;
}
