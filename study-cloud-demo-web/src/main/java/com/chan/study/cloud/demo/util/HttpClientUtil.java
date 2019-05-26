package com.chan.study.cloud.demo.util;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

@Slf4j
public class HttpClientUtil {

    private static Optional<HttpClientService> httpClientServiceOpt = Optional.empty();

    private static Supplier<? extends RuntimeException> rejectStrategy = ()-> new RuntimeException("未注入HttpClientService实例无法实装Util类");

    private static final Map<String, String> DEFAULT_HEADER_MAP;

    static {
        DEFAULT_HEADER_MAP = new HashMap<>();
        DEFAULT_HEADER_MAP.put("Content-type", "application/json; charset=utf-8");
    }

    public static void setHttpClientService(HttpClientService httpClientService){
        log.info("注入 HttpClientService 实例 ....");
        httpClientServiceOpt = Optional.ofNullable(httpClientService);
    }


    public static String get(String url) {
        return get(url,String.class);
    }

    public static <T> T get(String url, Class<T> clazz) {
        return httpClientServiceOpt.map(item -> item.get(url, null, DEFAULT_HEADER_MAP, clazz)).orElseThrow(rejectStrategy);
    }


    public static String post(String url) {
        return post(url, String.class);
    }

    public static <T> T post(String url, Class<T> clazz) {
        return post(url, (String) null, clazz);
    }

    public static <T> T post(String url, String body, Class<T> clazz) {
        return httpClientServiceOpt.map(item -> item.post(url, null, DEFAULT_HEADER_MAP, body, clazz)).orElseThrow(rejectStrategy);
    }




    public static <Resp> Resp post(String url, Map<String, String> params, Class<Resp> clazz) {
        return httpClientServiceOpt.map(item -> item.post(url, null, DEFAULT_HEADER_MAP, params, clazz)).orElseThrow(rejectStrategy);
    }


}