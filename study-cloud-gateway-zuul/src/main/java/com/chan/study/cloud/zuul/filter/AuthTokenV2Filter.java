package com.chan.study.cloud.zuul.filter;

import com.chan.study.cloud.authentication.consts.TokenConstant;
import com.chan.study.cloud.authentication.domain.LoginInfo;
import com.chan.study.cloud.zuul.rpc.AuthService;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 校验请求头token
 *
 * @author CtChan
 */
@Slf4j
@Component
public class AuthTokenV2Filter extends ZuulFilter {

    @Autowired
    private AuthService authService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER + 1;
    }

    @Override
    public boolean shouldFilter() {
        //todo 配置拦截链路
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        //校验逻辑
        //则获取request请求头token
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        String token = Optional.ofNullable(request.getHeader(TokenConstant.HEADER_TOKEN_KEY))
                //如果没有,则响应重新登录
                .filter(item->!ObjectUtils.isEmpty(item)).orElseGet(this::relogin);
        //获取token 对应的 sessionId
        Object sessionId = redisTemplate.opsForValue().get(TokenConstant.TOKEN_SESSION_MAPPING_KEY_PREFIX + token);
        if (!ObjectUtils.isEmpty(sessionId)) {
            // 如果有,判断将 sessionId放在请求头上;
            RequestContext.getCurrentContext().addZuulRequestHeader(TokenConstant.X_GLOBAL_SESSION_KEY, sessionId.toString());
            return null;
        }
        //todo 不相同:校验token并更新session
        //todo 如果没有,则校验token,并将token保存在本地session中
        localLoginProcess(token, request);
        return null;
    }

    private void localLoginProcess(String token, HttpServletRequest request) {
        //校验token有效性
        LoginInfo loginInfo = authService.verifyAndGetUserInfo(token);
        if (loginInfo == null || loginInfo.getUuid() == null) {
            relogin();
            return;
        }
        //将 localSession 转化为 globalSession(redis-session)
        HttpSession localSession = request.getSession();
        redisTemplate.opsForValue().set(TokenConstant.TOKEN_SESSION_MAPPING_KEY_PREFIX + token, localSession.getId(), TokenConstant.LOCAL_SESSION_LIVE_TIME, TimeUnit.SECONDS);
        localSession.setAttribute(TokenConstant.GLOBAL_SESSION_ROLES, Optional.of(loginInfo).map(LoginInfo::getRoles).orElse(Collections.emptyList()));
        localSession.setAttribute(TokenConstant.GLOBAL_SESSION_LOGIN_INFO, loginInfo);
        localSession.setMaxInactiveInterval(TokenConstant.LOCAL_SESSION_LIVE_TIME);
        RequestContext.getCurrentContext().addZuulRequestHeader(TokenConstant.X_GLOBAL_SESSION_KEY, localSession.getId());
    }

    /**
     * TODO 生成重定向登录链路
     */
    private String relogin() {
        //todo 内部实现RuntimeException,由ExceptionHandler去执行重定向
        return null;
    }

}
