package com.imooc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.annotations.ApiIgnore;

/**
 * @author qingtian
 * @description:
 * @Package com.immoc.controller
 * @date 2021/8/30 11:07
 */
@ApiIgnore
@RestController
public class HelloController {

    @GetMapping("hello")
    public Object hello() {
        return "hello world";
    }
}
