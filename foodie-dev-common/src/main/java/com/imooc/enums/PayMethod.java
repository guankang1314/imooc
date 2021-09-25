package com.imooc.enums;

import io.swagger.models.auth.In;

/**
 * @author qingtian
 * @description:
 * @Package com.imooc.enums
 * @date 2021/9/14 23:25
 */
public enum PayMethod {

    WEIXIN(1,"微信"),
    ALIPAY(2,"支付宝");

    public final Integer type;
    public final String value;

    PayMethod(Integer type,String value) {
        this.value = value;
        this.type = type;
    }
}
