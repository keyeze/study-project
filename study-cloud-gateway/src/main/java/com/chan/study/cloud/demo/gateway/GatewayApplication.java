package com.chan.study.cloud.demo.gateway;

import com.chan.study.cloud.demo.config.GatewayConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = "com.chan.study.cloud.gateway")
@EnableZuulProxy
@EnableDiscoveryClient
@Import({
        GatewayConfig.class
})
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class);
    }
}
