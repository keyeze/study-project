package com.chan.study.cloud.zuul.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import com.chan.study.cloud.authentication.consts.TokenConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.session.data.redis.RedisFlushMode;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;

@EnableRedisHttpSession(redisFlushMode = RedisFlushMode.IMMEDIATE)
public class SessionConfig {
    @Bean
    public HeaderHttpSessionIdResolver httpSessionStrategy() {
        return new HeaderHttpSessionIdResolver(TokenConstant.X_GLOBAL_SESSION_KEY);
    }
    @Bean
    public GenericFastJsonRedisSerializer springSessionDefaultRedisSerializer() {
        return new GenericFastJsonRedisSerializer();
    }
}
