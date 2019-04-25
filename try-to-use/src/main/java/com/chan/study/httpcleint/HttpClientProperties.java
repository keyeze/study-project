package com.chan.study.httpcleint;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "topmdrt.http.client.config")
public class HttpClientProperties {
    private String retryTime;
}
