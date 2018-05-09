package com.chan.study.spring.util;

import org.springframework.data.redis.core.RedisTemplate;

public interface IService<T> {
    T doService(RedisTemplate redisTemplate);
}
