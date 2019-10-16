package com.chan.study.cloud.authentication.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
@Data
@ConfigurationProperties(prefix = "auth.token")
public class TokenConfig {
    private String secret;

    private long surviveMs;
}
