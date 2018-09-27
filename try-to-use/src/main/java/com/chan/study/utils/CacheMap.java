package com.chan.study.utils;

import com.chan.study.config.CacheConfig;
import com.google.common.collect.MapMaker;
import org.junit.Assert;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * 缓存模板
 *
 * @param <V>
 * @param <T>
 *
 * @author King Ming
 */
public class CacheMap<T, V> {
    private ConcurrentMap<T, V> cacheMap;

    private CacheMap() {
        throw new RuntimeException();
    }

    public CacheMap(ObtainServer<? super T, ? extends V> obtainServer) {
        cacheMap = new MapMaker()
                .weakKeys()
                .weakValues()
                .expiration(1, TimeUnit.SECONDS)
                .makeComputingMap(obtainServer::apply);
    }

    public V get(T key) {
        V result = this.cacheMap.get(key);
        Assert.assertEquals("根据 ConcurrentHashMap 规范,不应该有空值传出", true, result != null);
        return result;
    }

    public static void main(String[] args) throws InterruptedException {
        CacheMap<String, Object> userInfo = new CacheConfig().demoInfoCache();
        System.out.println(userInfo.get("Chan"));
        System.out.println(userInfo.get("Chan"));
        Thread.sleep(1500);
        System.out.println(userInfo.get("Chan"));
        Thread.sleep(1500);
        System.out.println(userInfo.get("Chan"));
    }
}
