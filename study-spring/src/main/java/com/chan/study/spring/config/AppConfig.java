package com.chan.study.spring.config;

import com.chan.study.bean.OfflineBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean("offlineBeanInFullConfiguration")
    public OfflineBean getOfflineBean() {
        return new OfflineBean();
    }
}
