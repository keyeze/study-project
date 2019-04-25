package com.chan.study.httpcleint;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        HttpRequestRetryHandlerComponent.class
})
public class HttpClientConfig {
}
