package com.chan.study.cloud.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Hello world!
 */
@SpringBootApplication(
        scanBasePackages = "com.chan.study.cloud.eureka"
)
@EnableEurekaServer
@Configuration
@Import({
        ValidationAutoConfiguration.class
})
@Slf4j
public class EurekaApplication {
    public static void main(String[] args) {
//        SpringApplication.run(EurekaApplication.class);
        new SpringApplicationBuilder(EurekaApplication.class).web(WebApplicationType.SERVLET).run(args);
    }
}
