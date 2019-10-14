package com.chan.study.cloud.filter;

import com.chan.study.cloud.domain.AuthInfo;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Component
public class TokenLoginFilter extends ZuulFilter{
    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return false;
    }


    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String token = request.getHeader("X-AUTH-TOKEN");
        HttpSession localSession = request.getSession(false);
        if (token == null && localSession == null) {
            //重定向登录
            relogin();
        }
        if (localSession == null || token != null && token.equals(localSession.getAttribute("login-token"))) {
            //如果没有 session ,需要校验token信息
            //如果有session,判断session中的TOKEN信息与传入的TOKEN是否一致,
            //本地登录流程
            localLoginProcess(token, request.getSession());
        }else{
            // 获取zuul-session中的token
            //TODO 放行
        }
        return null;
    }

    /**
     * TODO 登录流程
     */
    private void localLoginProcess(String token, HttpSession session){
        //TODO 将 token 与 session-id 绑定
        AuthInfo authInfo = authToken(token);
        session.getId();
        //TODO 将 local-session-id 与 token 信息保存在 redis 中,用于免校验,默认为20分钟
        //TODO 将 global-session-id 与 token 信息保存在 redis 中,用于 session 共享,长时间内生效
        //TODO 刷新 session 和 token 存活时间
    }

    /**
     * TODO 生成重定向登录链路
     */
    private String relogin(){
        return null;
    }

    private AuthInfo authToken(String token){
        boolean success = true;
        // TODO 如果检验失败
        if (!success){
            relogin();
        }
        return null;
    }
}
