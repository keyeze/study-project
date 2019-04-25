package com.chan.study.cloud.stream.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {
    @Value("${eureka.instance.metadata-map.zone}")
    public String zone;
}
