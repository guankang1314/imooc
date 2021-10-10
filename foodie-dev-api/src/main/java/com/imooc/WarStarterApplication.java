package com.imooc;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author qingtian
 * @description:
 * @Package com.imooc
 * @date 2021/10/10 14:34
 */
//打包war  增加war的启动类
public class WarStarterApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {

        //指向Application这个Springboot启动类
        return builder.sources(Application.class);
    }
}
