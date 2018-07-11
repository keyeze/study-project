package com.chan.study.cloud.app;

import com.chan.study.cloud.config.RibbonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = "com.chan.study.cloud.web")
@EnableDiscoveryClient
@Import({
        RibbonConfig.class
})
public class RibbonApplication {
    public static void main(String[] args) {
        SpringApplication.run(RibbonApplication.class);
    }
}
