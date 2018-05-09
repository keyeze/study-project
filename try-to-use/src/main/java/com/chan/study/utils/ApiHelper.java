package com.chan.study.utils;

import com.alibaba.fastjson.JSONObject;

public interface ApiHelper {
    <T> T getResult(String relativePath, String data) throws Exception;
}
