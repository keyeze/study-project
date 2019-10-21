package com.chan.study.cloud.zuul.filter;

import com.chan.study.cloud.authentication.consts.TokenConstant;
import com.chan.study.cloud.authentication.domain.LoginInfo;
import com.chan.study.cloud.zuul.InfoBean;
import com.chan.study.cloud.zuul.consts.BusinessMainSessionKey;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * 将 token 转为 圆桌保服需要的 session 信息
 */
//@Component
public class TokenToSessionOfYZBFConvertFilter extends GatewayFilter {

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.RIBBON_ROUTING_FILTER_ORDER - 1;
    }

    @Override
    public Object run() throws ZuulException {
        //todo  根据表格装填业务相关的session信息;
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();

        Optional.ofNullable(request).map(item->item.getSession(false)).ifPresent(item->{
            LoginInfo loginInfo  = (LoginInfo) item.getAttribute(TokenConstant.GLOBAL_SESSION_LOGIN_INFO);
            //current_login_account             mall-admin-web	uuid	            这个uuid是企业微信的uuid
            //finance_web_current_login_account	finan-admin-web	uuid	            这个uuid是企业微信的uuidv

            //AGENT_SESSION	                    activity-web	agentSession	    这个存放的是agentSession对象
            //mall-web	                                        agentSession
            //order-web	                                        agentSession
            //user-web	                                        agentSession
            //CUSTOMER_SESSION	                mall-web	    customer_session

        });
        return null;
    }
}
