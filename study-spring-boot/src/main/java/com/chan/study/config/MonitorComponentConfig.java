package com.chan.study.config;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

//@Configuration
//@ComponentScan(
        //表明扫描范围
//        basePackages = "com.chan.study.monitor",
        //排除部分需要引入的文件
//        excludeFilters = {}
//)
public class MonitorComponentConfig {
//    @Bean
    public HealthIndicator eurekaHealthIndicator() {
        return new AbstractHealthIndicator() {
            @Override
            protected void doHealthCheck(Health.Builder builder) throws Exception {
                builder.down()
                        .withDetail("error cause", "no send message to eureka ...")
                        .withDetail("say something","Hello World~!");
            }
        };
    }
}
