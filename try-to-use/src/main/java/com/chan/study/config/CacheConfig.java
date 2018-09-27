package com.chan.study.config;

import com.chan.study.utils.BaseObtainServer;
import com.chan.study.utils.CacheMap;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

/**
 * @author King Ming
 */
@Configuration
public class CacheConfig {
    @Bean
    @ConditionalOnMissingBean(name = "demoInfoCache")
    public CacheMap<String, Object> demoInfoCache() {
        return new CacheMap<>(new BaseObtainServer<String, Object>() {
            @Override
            public Object secordQuery(String key) {
                return -1;
            }

            @Override
            public Object query(String key) {
                Random random = new Random();
                return random.nextInt(20) < 7 ? null : random.nextInt(30) + 20;
            }

            @Override
            public boolean isEmpty(Object result) {
                return result == null;
            }
        });

    }
}
