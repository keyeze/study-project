package com.chan.study.cloud.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * LOCAL_SESSION 转 GLOBAL_SESSION 信息
 */
@Component
public class TokenSessionConvertFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.RIBBON_ROUTING_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        //todo  根据表格装填业务相关的session信息;
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        if (request.getSession(false)!=null) {

        }
        return null;
    }
}
