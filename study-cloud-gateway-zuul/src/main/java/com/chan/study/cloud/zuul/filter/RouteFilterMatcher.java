package com.chan.study.cloud.zuul.filter;

import com.chan.study.cloud.zuul.config.GatewayFilterConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.Collections;
import java.util.Optional;


@Component
public class RouteFilterMatcher {
    @Autowired
    private GatewayFilterConfig gatewayFilterConfig;

    private PathMatcher pathMatcher = new AntPathMatcher();

    public <T extends GatewayFilter> boolean isMatch(T obj, String url) {
        GatewayFilterConfig.UrlRoute route = gatewayFilterConfig.getRoutes().getOrDefault(obj.self(), GatewayFilterConfig.DEFAULT_ROUTE);
        return route.getEnabled() &&
                Optional.of(route).map(GatewayFilterConfig.UrlRoute::getIncludePath).orElse(Collections.emptyList())
                        .stream().anyMatch(item -> pathMatcher.match(item, url)) &&
                Optional.of(route).map(GatewayFilterConfig.UrlRoute::getExcludeUrl).orElse(Collections.emptyList())
                        .stream().noneMatch(item -> pathMatcher.match(item, url));
    }

    public static void main(String[] args) {
        System.out.println(Collections.<String>emptyList()
                .stream().anyMatch(item -> new AntPathMatcher().match(item, "test/hello")));
    }
}
