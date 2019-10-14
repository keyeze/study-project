package com.chan.study.cloud.util;

import java.util.Map;

public interface HttpClientService {

    /**
     * GET 请求
     *
     * @param url    地址
     * @param clazz  封装成对象,默认实现JSON转化
     * @param header 请求头
     * @return 响应体
     */
    public <Resp> Resp get(String url, Map<String, String> requestParams, Map<String, String> header, Class<Resp> clazz);

    /**
     * POST请求
     *
     * @param url    地址
     * @param clazz  封装成对象,默认实现JSON转化
     * @param header 请求头
     * @param body   请求体
     * @return 响应体
     */
    public <Resp> Resp post(String url, Map<String, String> requestParams, Map<String, String> header, String body, Class<Resp> clazz);

    /**
     * POST 表单提交
     *
     * @param url    地址
     * @param clazz  封装成对象
     * @param header 请求头
     * @param params 表单
     * @return 响应体
     */
    public <Resp> Resp post(String url, Map<String, String> requestParams, Map<String, String> header, Map<String, String> params, Class<Resp> clazz);
}
