package com.chan.study.cloud.app;

import com.chan.study.cloud.config.TimeHelperConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = {
        "com.chan.study.cloud.demo",
})
//@EnableWebMvc
@Import({
        TimeHelperConfig.class
})
/**
 * Spring Cloud 实现了 eureka,consul,zookeeper 等 服务发现中心
 * {@link EnableDiscoveryClient} 基于 spring-cloud-commons 的 发现中心服务 可以串接其他搜索服务服务
 * {@link EnableEurekaClient} 基于 spring-cloud-netflix 的 发现中心服务 eureka
 */
@EnableDiscoveryClient
public class DemoWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoWebApplication.class, args);
    }
}
