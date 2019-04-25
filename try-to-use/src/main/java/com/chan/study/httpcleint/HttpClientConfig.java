package com.chan.study.httpcleint;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class HttpClientConfig {
    @Resource
    private HttpClientProperties httpClientProperties;

    @Bean
    public String string(){
        return httpClientProperties.getRetryTime();
    }
}
