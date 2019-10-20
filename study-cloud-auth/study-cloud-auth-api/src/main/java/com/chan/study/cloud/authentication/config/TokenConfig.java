package com.chan.study.cloud.authentication.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author CtChan
 */
@Configuration
@PropertySource("classpath:/auth.properties")
@ConfigurationProperties(prefix = "auth.token")
public class TokenConfig {
    private String secret;

    private Long surviveMs;

    public String getSecret() {
        return secret;
    }

    public TokenConfig setSecret(String secret) {
        this.secret = secret;
        return this;
    }

    public Long getSurviveMs() {
        return surviveMs;
    }

    public TokenConfig setSurviveMs(Long surviveMs) {
        this.surviveMs = surviveMs;
        return this;
    }
}
