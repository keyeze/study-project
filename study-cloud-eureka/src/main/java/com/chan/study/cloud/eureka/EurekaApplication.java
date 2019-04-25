package com.chan.study.cloud.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Configuration;

/**
 * Hello world!
 *
 * @author Chan
 */
@SpringBootApplication(
        scanBasePackages = "com.chan.study.cloud.eureka"
)
@EnableEurekaServer
@Configuration
public class EurekaApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class);
//        new SpringApplicationBuilder(EurekaApplication.class).web(WebApplicationType.SERVLET).run(args);
    }
}
