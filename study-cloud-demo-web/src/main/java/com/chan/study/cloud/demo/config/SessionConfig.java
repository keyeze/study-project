package com.chan.study.cloud.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;

@EnableRedisHttpSession
public class SessionConfig {
    @Bean
    public HeaderHttpSessionIdResolver httpSessionStrategy() {
        return new HeaderHttpSessionIdResolver("X-GLOBAL-SESSION");
    }
}
