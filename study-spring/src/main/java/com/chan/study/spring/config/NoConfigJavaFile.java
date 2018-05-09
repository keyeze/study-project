package com.chan.study.spring.config;

import com.chan.study.bean.OfflineBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class NoConfigJavaFile {
    @Bean("offlineBeanLite")
    public OfflineBean offlineBean() {
        return new OfflineBean();
    }
}
