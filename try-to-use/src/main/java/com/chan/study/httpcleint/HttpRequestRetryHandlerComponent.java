package com.chan.study.httpcleint;

import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.StandardHttpRequestRetryHandler;
import org.springframework.context.annotation.Bean;

/**
 * HttpClient 自定义请求重试适配器
 * HttpClient 提供的两种适配器:
 * {@link DefaultHttpRequestRetryHandler} 和 {@link StandardHttpRequestRetryHandler}
 */
public class HttpRequestRetryHandlerComponent {
    @Bean
    public HttpRequestRetryHandlerComponent customizeHttpRequestRetryHandler(){
        return null;
    }
}
