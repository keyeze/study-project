package com.chan.study.cloud.demo.util;

import lombok.Data;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Component
@PropertySource("classpath:http-client.yml")
@ConfigurationProperties(prefix = "topmdrt.http.client.config")
public class HttpClientProperties {
    public Integer socketTimeout;
    private Integer maxCountTotal;
    private Integer maxConnPerRoute;
    private int validateAfterInactivity;
    private Map<String,String> headers;
    private int connectionRequestTimeout;
    private int readTimeout;
    private int connectTimeout;

    public List<Header> getHeaderList(){
        if (!Optional.ofNullable(headers).isPresent()) {
            return null;
        }
        new SpringApplication(HttpClientService.class);
        return headers.entrySet().stream().map(item->new BasicHeader(item.getKey(),item.getValue())).collect(Collectors.toList());
    }
}
