package com.chan.study.cloud.authentication.service;

import com.chan.study.cloud.authentication.config.TokenConfig;
import com.chan.study.cloud.authentication.domain.LoginSessionDto;
import com.chan.study.cloud.authentication.util.JwtHelper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class AuthService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private TokenConfig tokenConfig;
    @Autowired
    private RoleService roleService;

    @Value("${login.session.survive.ms}")
    private long surviveTime;

    public LoginSessionDto getUserInfoByToken(String token) {
        Payload payload = validAndRefreshToken(token);
        //todo: 获取uuid,查询uuid对应的role权限
        LoginSessionDto loginSessionDto = new LoginSessionDto();
        loginSessionDto.setBirth(payload.getTimestamp());
        loginSessionDto.setUuid(payload.getUuid());
        loginSessionDto.setRoles(roleService.getRolesByUuid(payload.getUuid()));
        return loginSessionDto;
    }

    public void changeRolesByToken(String token, List<String> roles) {
        Payload payload = validAndRefreshToken(token);
        roleService.changeRolesByUuid(payload.getUuid(),roles);
    }

    @Data
    private class Payload extends com.chan.study.cloud.authentication.domain.Payload {
        String toRedisKey() {
            return "auth:token:" + getUuid() + ":" + getTimestamp() + ":" + getRandom();
        }
    }

    public Payload validAndRefreshToken(String token){
        //校验 token / 获取 token:payload 信息
        Payload payload = JwtHelper.getPayload(tokenConfig.getSecert(), token, Payload.class);
        if (!redisTemplate.opsForValue().setIfPresent(payload.toRedisKey(),true,surviveTime, TimeUnit.MILLISECONDS)) {
            throw new RuntimeException("登录失效");
        }
        return payload;
    }
}
