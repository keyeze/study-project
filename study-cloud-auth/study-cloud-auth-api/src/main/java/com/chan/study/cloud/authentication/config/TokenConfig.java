package com.chan.study.cloud.authentication.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@RefreshScope
@Data
@PropertySource("classpath:auth.yml")
@ConfigurationProperties(prefix = "topmdrt.http.client.config")
public class TokenConfig {
    private String secert;
}
