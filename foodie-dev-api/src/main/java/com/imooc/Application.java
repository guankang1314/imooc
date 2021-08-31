package com.imooc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author qingtian
 * @description:
 * @Package com.immoc
 * @date 2021/8/30 11:04
 */
@SpringBootApplication
@MapperScan(basePackages = "com.imooc.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
