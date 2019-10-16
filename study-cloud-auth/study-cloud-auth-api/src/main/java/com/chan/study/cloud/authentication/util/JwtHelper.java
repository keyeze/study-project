package com.chan.study.cloud.authentication.util;

import com.alibaba.fastjson.JSON;
import com.chan.study.cloud.authentication.domain.Payload;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;

@Slf4j
public class JwtHelper {
    public static Payload getPayload(String key, String token) {
        return JSON.parseObject(
                JSON.toJSONString(Jwts.parser()
                        .setSigningKey(generalKey(key))
                        .parseClaimsJws(token)
                        .getBody()
                ), Payload.class);
    }

    public static <T> T getPayload(String key, String token, Class<T> clz) {
        return JSON.parseObject(
                JSON.toJSONString(Jwts.parser()
                        .setSigningKey(generalKey(key))
                        .parseClaimsJws(token)
                        .getBody()
                ), clz);
    }

    public static String buildToken(String key, Payload payload) {
        String token = Jwts.builder().setHeaderParam("type", "jwt")
                .setHeaderParam("alg", "HS512")
                .setClaims(payload.toJSON())
                .signWith(SignatureAlgorithm.HS512, generalKey(key)).compact();
        log.info("将payload[{}]信息生成token[{}]", payload, token);
        return token;
    }

    private static String generalKey(String key) {
        try {
            return DigestUtils.md5DigestAsHex(key.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            log.error("", e);
            throw new RuntimeException("test");
        }
    }
}