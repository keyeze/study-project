package com.chan.study.spring.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {
    private static final long DEFAULT_EXPIRES = Long.MAX_VALUE;
    public static final int NO_EXPIRE = -1;

    /**
     * 锁等待时间，防止线程饥饿
     */
    private int timeoutMsecs = 10 * 1000;

    private RedisTemplate redisTemplate;


    public boolean tryLock(String key, int expireMsecs, TimeUnit timeUnit) {
        return tryLockAndRun(key, (redisTemplate) -> true, expireMsecs, timeUnit);
    }

    /**
     * 尝试获得锁并运行结果
     *
     * @param redisTemplate redis封装操作对象
     * @param lockKey       锁定资源Key,要保证请求同一资源的Key要相同
     * @param expireMsecs   资源超时时间,超过这个时间可以重新存入redis
     * @param timeUnit
     *
     * @return 是否存储成功
     */
    public <T> T tryLockAndRun(String key, IService<T> service, int expireMsecs, TimeUnit timeUnit) {
        boolean flag = false;
        T result = null;
        //推算锁失效时间
        long expires = DEFAULT_EXPIRES;
        if (expireMsecs > 0) {
            expires = System.currentTimeMillis() + timeUnit.toMillis(expireMsecs) + 1;
        }
        String expiresStr = String.valueOf(expires); // 锁到期时间
        //保存锁,如果成功返回
        if (redisTemplate.opsForValue().setIfAbsent(key, expiresStr)) {
            flag = true;
        }
        //如果不成功,判断是否已经过了对象存储的失效时间
        String currentValueStr = (String) redisTemplate.opsForValue().get(key); // redis里的时间
        if (currentValueStr != null && Long.parseLong(currentValueStr) < System.currentTimeMillis()) {
            //成功获得资源
            // 获取上一个锁到期时间，并设置现在的锁到期时间
            String oldValueStr = (String) redisTemplate.opsForValue().getAndSet(key, expiresStr);

            // 只有一个线程才能获取上一个线上的设置时间，因为jedis.getSet是同步的
            if (oldValueStr != null && oldValueStr.equals(currentValueStr)) {
                flag = true;
            }
        }
        if (flag) {
            result = service.doService(redisTemplate);
        }
        return result;

    }

    public <T> T tryLockAndRun(String key, IService<T> service) {
        return tryLockAndRun(key, service, NO_EXPIRE, TimeUnit.NANOSECONDS);
    }

    public boolean tryLock(String key) {
        return tryLock(key, NO_EXPIRE, TimeUnit.NANOSECONDS);
    }

    public boolean lock(String key) {

        return true;
    }

}
