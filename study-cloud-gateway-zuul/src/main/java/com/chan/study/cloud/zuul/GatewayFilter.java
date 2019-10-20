package com.chan.study.cloud.zuul;

import com.chan.study.cloud.zuul.filter.RouteFilterMatcher;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class GatewayFilter extends ZuulFilter implements BeanNameAware {
    private String name;
    @Autowired
    private RouteFilterMatcher matcher;
    public String self() {
        return name;
    }

    @Override
    public void setBeanName(String s) {
        this.name = s;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        return matcher.isMatch(this, requestContext.getRequest().getRequestURI());
    }
}
