package com.chan.study.cloud.zuul.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.util.*;

@Data
@ConfigurationProperties("gateway-filter")
public class GatewayFilterConfig {

    public static final UrlRoute DEFAULT_ROUTE ;
    static {
        DEFAULT_ROUTE = new UrlRoute();
        DEFAULT_ROUTE.setEnabled(false);
    }

    private Map<String,UrlRoute> routes = new LinkedHashMap<>();

    @PostConstruct
    public void init(){
        routes.forEach((key,value)->{
            if (!Optional.ofNullable(value.getFilterName()).isPresent()) {
                value.setFilterName(key);
            }
        });
    }
    @Data
    public static class UrlRoute{
        private Boolean enabled = true;
        private String filterName;
        private List<String> includePath;
        private List<String> excludeUrl;
        private String filterClass;
    };
}
