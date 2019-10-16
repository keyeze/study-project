package com.chan.study.cloud.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.EnableZuulServer;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Spring Cloud 实现了 eureka,consul,zookeeper 等 服务发现中心
 * {@link EnableDiscoveryClient} 基于 spring-cloud-commons 的 发现中心服务 可以串接其他搜索服务服务
 * {@link EnableEurekaClient} 基于 spring-cloud-netflix 的 发现中心服务 eureka
 *
 * @author CtChan
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableZuulProxy
public class ZuulApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
    }
}
