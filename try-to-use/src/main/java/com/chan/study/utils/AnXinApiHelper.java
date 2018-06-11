package com.chan.study.utils;

import com.alibaba.fastjson.JSONObject;


public class AnXinApiHelper implements ApiHelper {
    private final String baseUrl = "http://test.mall.ssinsure.cn:8888/api/v1.0/";
    private final String planId = "23";
    private final String userCode = "SSHZ-HB-0001";
    private final String publicKey = "b2s4j65d";
    private final String privateKey = "Acc0006a";

    @Override
    public JSONObject getResult(String relativePath, String data) throws Exception {
        String encryptData = DESUtils.encryptCBC(data, publicKey, DESUtils.CHARSET);
        System.out.println(baseUrl + relativePath);
        System.out.println(encryptData);
        String originalResult = HttpRequest.POST(baseUrl + relativePath, encryptData);
        JSONObject decryptResult = JSONObject.parseObject(DESUtils.decryptCBC(originalResult, publicKey, DESUtils.CHARSET));
        if (0 == (Integer) decryptResult.get("code")) {
            String decryptMessage = DESUtils.decryptCBC((String) decryptResult.get("message"), privateKey, DESUtils.CHARSET);
            decryptResult.put("message", decryptMessage);
        }
        return decryptResult;
    }

    public static void main(String[] args) throws Exception {
//      String request = "{\"parameter\":{\"area\":\"350000\",\"birthday\":\"1971-06-29\",\"cardNo\":\"330783197106297525\",\"cardType\":\"120001\",\"code\":\"1008\",\"email\":\"keyeze@126.com\",\"insBeginDate\":\"2018-05-11\",\"insEndDate\":\"2019-05-10\",\"insuredBirthday\":\"1971-06-29\",\"insuredCardNo\":\"330783197106297525\",\"insuredCardType\":\"120001\",\"insuredName\":\"高迎南\",\"insuredRelationship\":\"601005\",\"mobile\":\"15959028475\",\"name\":\"高迎南\",\"socialSecFlag\":\"Y\"},\"planId\":\"23\",\"userCode\":\"SSHZ-HB-0001\",\"option\":{\"amount\":\"300\",\"social\":1,\"day\":\"1\"}}";
//        System.out.println(new AnXinApiHelper().getResult("verify.json", request));

        String request2 = "{\"planId\":\"23\",\"userCode\":\"SSHZ-HB-0001\"}";
        System.out.println(new AnXinApiHelper().getResult("get_settings.json", request2));

        String request3 = "{\"planId\":\"23\",\"userCode\":\"SSHZ-HB-0001\"}";
        System.out.println(new AnXinApiHelper().getResult("get_doc.json", request3));

//        String request4 = "{\"parameter\":{\"area\":\"350000\",\"birthday\":\"1971-06-29\",\"cardNo\":\"330783197106297525\",\"cardType\":\"120001\",\"code\":\"1008\",\"email\":\"keyeze@126.com\",\"insBeginDate\":\"2018-05-11\",\"insEndDate\":\"2019-05-10\",\"insuredBirthday\":\"1971-06-29\",\"insuredCardNo\":\"330783197106297525\",\"insuredCardType\":\"120001\",\"insuredName\":\"高迎南\",\"insuredRelationship\":\"601005\",\"mobile\":\"15959028475\",\"name\":\"高迎南\",\"socialSecFlag\":\"Y\"},\"planId\":\"23\",\"userCode\":\"SSHZ-HB-0001\",\"option\":{\"amount\":\"300\",\"social\":1,\"day\":\"1\"}}";
//        System.out.println(new AnXinApiHelper().getResult("order.json", request4));/**/

//        String str = "{\"parameter\":{\"area\":\"310000\",\"birthday\":\"1947-05-12\",\"cardNo\":\"44030519470512001X\",\"cardType\":\"120001\",\"code\":\"1012\",\"email\":\"15959028475@qq.com\",\"insBeginDate\":\"2018-05-12\",\"insEndDate\":\"2019-05-11\",\"insuredBirthday\":\"1947-05-12\",\"insuredCardNo\":\"44030519470512001X\",\"insuredCardType\":\"120001\",\"insuredName\":\"陈祖强\",\"insuredRelationship\":\"601005\",\"mobile\":\"15959028475\",\"name\":\"陈祖强\",\"socialSecFlag\":\"Y\"},\"planId\":\"23\",\"userCode\":\"SSHZ-HB-0001\",\"option\":{\"amount\":\"300\",\"social\":\"1\",\"day\":\"1\"}}";


    }
}
