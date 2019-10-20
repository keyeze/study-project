package com.chan.study.cloud.authentication.config;

import com.chan.study.cloud.authentication.consts.TokenConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;

@EnableRedisHttpSession
public class SessionConfig {
    @Bean
    public HeaderHttpSessionIdResolver httpSessionStrategy() {
        return new HeaderHttpSessionIdResolver(TokenConstant.X_GLOBAL_SESSION_KEY);
    }
}
