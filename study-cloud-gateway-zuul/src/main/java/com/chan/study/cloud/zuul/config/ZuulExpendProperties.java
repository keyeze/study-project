package com.chan.study.cloud.zuul.config;

import com.chan.study.cloud.zuul.filter.route.FiltersWrapper;
import com.chan.study.cloud.zuul.filter.route.RouteFilter;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;

import java.util.*;
import java.util.stream.Collectors;

@Data
@ConfigurationProperties("zuul")
public class ZuulExpendProperties implements FiltersWrapper {
    private Map<String,ZuulExpendRoute> routes;

    //todo 观察顺序性
    @Autowired
    private Map<String,RouteFilter> registeredFilters = new LinkedHashMap<>();

    public Map<String, ZuulExpendRoute> getRoutes() {
        return routes;
    }

    public void setRoutes(Map<String, ZuulExpendRoute> routes) {
        this.routes = routes;
    }

    @Override
    public List<RouteFilter> getFilters(String key) {
        return Optional.ofNullable(routes).map(item -> item.get(key)).map(ZuulExpendRoute::getFilters).orElse(Collections.emptyList()).stream()
                .map(registeredFilters::get).collect(Collectors.toList());
    }

    @Data
    public static class ZuulExpendRoute extends ZuulProperties.ZuulRoute {
        //todo 观察顺序性
        private List<String> filters = new ArrayList<>();
    }

}
