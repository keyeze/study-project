package com.chan.study.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class AnXinApiHelper implements ApiHelper {
    private final String baseUrl = "http://test.mall.ssinsure.cn:8888/api/v1.0/";
    private final String parentId = "23";
    private final String userCode = "SSHZ-HB-0001";
    private final String publicKey = "b2s4j65d";
    private final String privateKey = "Acc0006a";

    @Override
    public JSONObject getResult(String relativePath, String data) throws Exception {
        String encryptData = DESUtils.encryptCBC(data, publicKey, DESUtils.CHARSET);
        String originalResult = HttpRequest.POST(baseUrl + relativePath, encryptData);
        JSONObject decryptResult = JSONObject.parseObject(DESUtils.decryptCBC(originalResult, publicKey, DESUtils.CHARSET));
        if (0 == (Integer) decryptResult.get("code")) {
            String decryptMessage = DESUtils.decryptCBC((String) decryptResult.get("message"), privateKey, DESUtils.CHARSET);
            decryptResult.put("message", decryptMessage);
        }
        return decryptResult;
    }
}
