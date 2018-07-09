package com.chan.study.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(
        //表明扫描范围
        basePackages = "com.chan.study.web",
        //排除部分需要引入的文件
        excludeFilters = {}
)
public class WebConfig {

}
