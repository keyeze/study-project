package com.chan.study.cloud.gateway.service;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;

public class GatewayFilter extends ZuulFilter {
    enum FilterType {
        /**
         * PRE      ：路由前，
         * ROUTING  ：路由时，
         * POST     ：路由后，
         * ERROR    ：错误时
         */
        PRE, ROUTING, POST, ERROR;

        public String of() {
            return this.name().toLowerCase();
        }
    }

    @Override
    public String filterType() {
        return FilterType.PRE.of();
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * a "true" return from this method means that the run() method should be invoked
     *
     * @return true if the run() method should be invoked. false will not invoke the run() method
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * if shouldFilter() is true, this method will be invoked. this method is the core method of a ZuulFilter
     *
     * @return Some arbitrary artifact may be returned. Current implementation ignores it.
     * @throws ZuulException if an error occurs during execution.
     */
    @Override
    public Object run() throws ZuulException {
        return null;
    }
}
