package com.chan.study.cloud.authentication.service;

import com.chan.study.cloud.authentication.config.TokenConfig;
import com.chan.study.cloud.authentication.domain.LoginInfo;
import com.chan.study.cloud.authentication.util.JwtHelper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class AuthService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private TokenConfig tokenConfig;
    @Autowired
    private RoleService roleService;

    public LoginInfo getUserInfoByToken(String token) {
        Payload payload = validAndRefreshToken(token);
        //获取uuid,查询uuid对应的role权限
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setBirth(payload.getTimestamp() + "");
        loginInfo.setUuid(payload.getUuid());
        loginInfo.setRoles(roleService.getRolesByUuid(payload.getUuid()));
        return loginInfo;
    }

    public void changeRolesByToken(String token, List<String> roles) {
        Payload payload = validAndRefreshToken(token);
        roleService.changeRolesByUuid(payload.getUuid(), roles);
    }

    @Data
    private class Payload extends com.chan.study.cloud.authentication.domain.Payload {
        String toRedisKey() {
            return "auth:token:" + getUuid() + ":" + getTimestamp() + ":" + getRandom();
        }

        String toRedisKeysOfUuid() {
            return "auth:token:" + getUuid() + ":*";
        }

    }

    public Payload validAndRefreshToken(String token) {
        //校验 token / 获取 token:payload 信息
        Payload payload = JwtHelper.getPayload(tokenConfig.getSecert(), token, Payload.class);
        if (!redisTemplate.opsForValue().setIfPresent(payload.toRedisKey(), "true", tokenConfig.getSurviveMs(), TimeUnit.MILLISECONDS)) {
            throw new RuntimeException("登录失效");
        }
        return payload;
    }

    public String createToken(String uuid) {
        long current = System.currentTimeMillis();
        long random = (long) (Math.random() * 10000L);
        Payload payload = new Payload();
        payload.setUuid(uuid);
        payload.setTimestamp(current + "");
        payload.setRandom(random + "");
        //如果redis有,则从redis取
        Set<String> keys = redisTemplate.keys(payload.toRedisKeysOfUuid());
        if (keys != null) {
            keys.stream().filter(item -> item.matches("auth:token:\\w+:\\d+:\\d+")).findFirst().ifPresent(item -> {
                String[] temp = item.split(":");
                payload.setTimestamp(temp[3]);
                payload.setRandom(temp[4]);
            });

        }
        //否则重新生成
        return JwtHelper.buildToken(tokenConfig.getSecert(), payload);

    }
}
