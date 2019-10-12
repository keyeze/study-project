package com.chan.study.cloud.authentication.service;

import com.chan.study.cloud.authentication.domain.LoginSessionDto;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class AuthService {
    @Autowired
    private RedisTemplate<String,Boolean> redisTemplate;

    @Value("${login.session.survive.ms}")
    private long surviveTime;

    public LoginSessionDto getUserInfoByToken(String token) {
        //todo : 校验 token
        //todo : 获取 token:payload 信息
        Payload payload = new Payload() {{
            setRandom(123L);
            setTimestamp(100L);
            setUuid("test");
        }};

        if (!redisTemplate.opsForValue().setIfPresent(payload.toRedisKey(),true,surviveTime, TimeUnit.MILLISECONDS)) {
            throw new RuntimeException("登录失效");
        }

        return null;
    }

    @Data
    private class Payload{
        private String uuid;
        private long timestamp;
        private long random;

        public String toRedisKey() {
            return "auth:token:" + uuid + ":" + timestamp + ":" + random;
        }
    }
}
