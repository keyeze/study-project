package com.chan.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class TryUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(TryUserApplication.class, args);
    }
}
