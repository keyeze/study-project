package com.chan.study.cloud.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author CtChan
 */
@Slf4j
@Service("topmdrtHttpClientService")
public class TopmdrtHttpClientServiceImpl implements HttpClientService {

    @Autowired
    private RestTemplate restTemplate;

    private final Map<String, String> JSON_HEADER_MAP;

    {
        JSON_HEADER_MAP = new HashMap<>();
        JSON_HEADER_MAP.put("Content-type", "application/json; charset=utf-8");
    }

    @PostConstruct
    public void registerUtil() {
        HttpClientUtil.setHttpClientService(this);
    }

    /**
     * GET 请求
     *
     * @param url           地址
     * @param requestParams url请求参数
     * @param header        请求头
     * @param respClz         封装成对象,默认实现JSON转化
     * @return
     */
    @Override
    public <Resp> Resp get(String url, Map<String, String> requestParams, Map<String, String> header, Class<Resp> respClz) {
        return requestSync(url, requestParams, HttpMethod.GET, header, null, respClz);
    }

    /**
     * POST请求
     *
     * @param url           地址
     * @param requestParams url请求参数
     * @param header        请求头
     * @param body          请求体
     * @param respClz         封装成对象,默认实现JSON转化
     * @return
     */
    @Override
    public <Resp> Resp post(String url, Map<String, String> requestParams, Map<String, String> header, String body, Class<Resp> respClz) {
        return requestSync(url, requestParams, HttpMethod.POST, header, body, respClz );
    }

    /**
     * POST 表单提交
     *
     * @param url           地址
     * @param requestParams url请求参数
     * @param header        请求头
     * @param params        表单
     * @param respClz       响应类
     * @param <Resp>        响应类型
     * @return 响应对象
     */
    @Override
    public <Resp> Resp post(String url, Map<String, String> requestParams, Map<String, String> header, Map<String, String> params, Class<Resp> respClz) {
        // 创建请求体
        return requestSync(url, requestParams, HttpMethod.POST, header, params, respClz);
    }

    private <Req, Resp> Resp requestSync(String url, Map<String, String> requestParams, HttpMethod method, Map<String, String> headerMap, Req body, Class<Resp> respClz) {
        // 加入请求头
        HttpHeaders header = new HttpHeaders();
        Object bodyEntity = resloveMessage(body);
        Optional.ofNullable(headerMap).ifPresent(item -> item.forEach(header::add));
        HttpEntity httpEntity = new HttpEntity<>(bodyEntity, header);
        return this.exchange(url,method,httpEntity,respClz,requestParams);
    }



    private <T> Object resloveMessage(T params) {
        if (params==null){
            return params;
        }
        //todo 阅读 Spring 源码利用MessageConverter解决数据转化问题。
        if (params instanceof Map) {
            MultiValueMap<String, String> result = new LinkedMultiValueMap<>();
            Optional.of((Map) params).orElseGet(Collections::emptyMap).forEach((key, value) -> result.add(key.toString(), value.toString()));
            return result;
        }
        if (params instanceof String) {
            return params;
        }
        throw new RuntimeException("can not resolve request body to convert...");
    }

    private <Resp> Resp exchange(String url, HttpMethod method, HttpEntity<?> httpEntity, Class<Resp> respClz,  Map<String, String> requestParams){
//        String key = "outRestTemplate";
//        if (url.matches("^\\w+://.+$")) {
//            if (url.startsWith("feign")) {
//                key = "restTemplate";
//                url = url.replaceFirst("feign","http");
//            }
//        }
        if (requestParams == null) {
            return restTemplate.exchange(url, method, httpEntity, respClz).getBody();
        }
        return restTemplate.exchange(url, method, httpEntity, respClz, requestParams).getBody();
    }
}