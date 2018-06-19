package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dafy.composite.commons.utils.sign.SignUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {
    private        long   time;
    private static String publicKey  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCAMffwMFM2CW3WA8tisgOpD1Cnxw4722fPlKUW1sANBjNgFJkzGL+y/94419EFt1XFmqtDnw6ECS8LxYL7YSc1TVbGCgT+JD4DfKYTExBtW/GVA5Yu9lb9aK9mpedeIVJloU5Iq4szZ+CNB13DdSrLnJogzMz94HKwfiSLYZBVoQIDAQAB";
    private static String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIAx9/AwUzYJbdYDy2KyA6kPUKfHDjvbZ8+UpRbWwA0GM2AUmTMYv7L/3jjX0QW3VcWaq0OfDoQJLwvFgvthJzVNVsYKBP4kPgN8phMTEG1b8ZUDli72Vv1or2al514hUmWhTkirizNn4I0HXcN1KsucmiDMzP3gcrB+JIthkFWhAgMBAAECgYAd5YlhJqQBUimfY7snBUT9RGkW98FGDGEldBEcRnD7mJqaqbMgy4DJigqVTx+cKamDMBMI7itAd7PVFvb5EStHWIaI/5gxJSUyZnpLSZBfo84PJdVtXnAbhZ3NJxs7poBbWb8/kLlVfxjk0vXiADq5A1nfIns9MjvuW0ESkpzXAQJBAOEaBoI4tIuQgH4V1x0t7fur2gOPrGf0ZuJRCx10LMFivEI2FIl256ZH3nYF/48btpBXg1JI8Za7pHz7kKREDTkCQQCRyrEXFk7d6icUDuXv7XcQt5PxJGeO5Xg2fA7wW5A3ETJY9EVI/Xq2+H2YMsQhm7DIrYCp2ACJ4WE9Zqd3S3OpAkAYPgdJavX2ud88tPlvyQyCOCXIkGaO44FZCkVaLLLNOObxcoWPsGCORdstdsPpE6D7tpEMAZMTGq5CT41qQ3HxAkALJkksztPxPLsIWatUEgENEj0KMBKLZxkucYZi812wGGyVSPkTf+8mlxJj1V4Sg+mdL0ertY00/juFipg8E1UhAkEAh08Qs76MFptcD5xnj9VK8/jJLqRaaTwGBsyeoS5A9ShXCPKZLIQtSxDS0I4XXjVvsneKywaKAzjYD+D6ORGPVA==";

    @PostMapping("/notify")
    public String testNotify(@RequestBody String context) throws Exception {
        String jsonStr = RSAEncryptUtils
                .decryptByPrivateKey(Base64Utils.decode(context), privateKey);
        System.out.println(jsonStr);
        JSONObject json = JSONObject.parseObject(jsonStr);
        String signStr = (String) json.remove("sign");
        String signTypeStr = (String) json.remove("signType");
        StringBuilder sb = new StringBuilder();
        for (Iterator<Map.Entry<String, Object>> it = json.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, Object> entry = it.next();
            sb.append(entry.getKey()).append("=")
                    .append(JSONObject.toJSONString(entry.getValue(), SerializerFeature.SortField))
                    .append("&");
        }
        sb.deleteCharAt(sb.lastIndexOf("&"));
        System.out.println(sb);
        byte[] data = com.dafy.composite.commons.utils.encript.Base64Utils
                .encode(sb.toString().getBytes("UTF-8"));
        boolean result = SignUtils.verify(data, publicKey, signStr,
                SignUtils.SignatureAlgorithm.SHA1_WITH_RSA);
        if (!result) {
            System.out.println("FAIL");
            return "FAIL";
        }
        System.out.println("SUCCESS");
        System.out.println(jsonStr);
        return "SUCCESS";
    }

    @PostMapping("/notify/{key}")
    public String testNotify(@RequestBody String context, @PathVariable("key") String key)
            throws Exception {
        System.out.println(key);
        return testNotify(context);
    }

    public long getTime() {
        if (time <= 0) {
            time = System.currentTimeMillis();
        }
        return System.currentTimeMillis() - time;
    }

    //    public static void main(String[] args) throws Exception {
    public void ff() throws Exception {
        Map<String, Object> keyMap = RSAEncryptUtils.genKeyPair();
        //        String publicKey = RSAEncryptUtils.getPublicKey(keyMap);
        //        String privateKey = RSAEncryptUtils.getPrivateKey(keyMap);
        System.err.println("公钥: " + publicKey);
        System.err.println("私钥： " + privateKey);

        String source = "这是一行测试RSA数字签名的无意义文字";
        System.out.println("原文字：" + source);
        byte[] data = source.getBytes("UTF-8");
        String temp1 = RSAEncryptUtils.encryptByPublicKey(data, publicKey);
        Base64Utils.encode(temp1.getBytes("UTF-8"));
        System.out.println("加密后：" + temp1);

        String temp2 = RSAEncryptUtils.decryptByPrivateKey(Base64Utils.decode(temp1), privateKey);
        System.out.println("解密后: " + temp2);

    }

    public static void main(String[] args) throws Exception {
    }

}
