package com.imooc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imooc.pojo.bo.ShopcartBO;
import com.imooc.utils.IMOOCJSONResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author qingtian
 * @description:
 * @Package com.imooc.controller
 * @date 2021/9/11 16:44
 */
@Api(value = "购物车接口controller",tags = {"购物车相关接口api"})
@RestController
@RequestMapping("shopcart")
public class ShopCatController {

    @ApiOperation(value = "添加商品到购物车",notes = "添加商品到购物车",httpMethod = "POST")
    @PostMapping("/add")
    public IMOOCJSONResult add(@RequestParam String userId,
                                @RequestBody ShopcartBO shopcartBO,
                                HttpServletRequest request,
                                HttpServletResponse response) {

        if (StringUtils.isBlank(userId)) {
            return IMOOCJSONResult.errorMsg(null);
        }

        System.out.println(shopcartBO);
        //TODO 前端用户在登录的情况下，添加商品到购物车，后端会同步到redis缓存中

        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "购物车商品删除",notes = "购物车商品删除",httpMethod = "POST")
    @PostMapping("/del")
    public IMOOCJSONResult del(@RequestParam String userId,
                                @RequestParam String itemSPecId,
                                HttpServletRequest request,
                                HttpServletResponse response) {

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(itemSPecId)) {
            return IMOOCJSONResult.errorMsg("参数不能为空！");
        }

        System.out.println(itemSPecId);
        //TODO 前端用户在登录的情况下，删除商品到购物车，后端会同步到redis缓存中

        return IMOOCJSONResult.ok();
    }
}
