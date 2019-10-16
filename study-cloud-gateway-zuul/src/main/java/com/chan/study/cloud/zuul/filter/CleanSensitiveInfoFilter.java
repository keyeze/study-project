package com.chan.study.cloud.zuul.filter;

import com.chan.study.cloud.authentication.consts.TokenConstant;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class CleanSensitiveInfoFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.SEND_RESPONSE_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        //todo 清除共享session信息
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        if (request.getSession(false) != null) {
            request.getSession(false).removeAttribute(TokenConstant.X_GLOBAL_SESSION_KEY);
        }
        return null;
    }
}
