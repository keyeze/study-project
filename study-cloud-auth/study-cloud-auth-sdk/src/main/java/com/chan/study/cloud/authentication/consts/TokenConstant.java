package com.chan.study.cloud.authentication.consts;

import com.chan.study.cloud.authentication.warm.ConstantClass;

/**
 * @author CtChan
 */
@ConstantClass
public interface TokenConstant {
    /**
     * 获取请求头携带token的key
     */
    String HEADER_TOKEN_KEY = "AUTH-TOKEN";
    /**
     * session中临时保存的token的key
     */
    String LOCAL_SESSION_KEY = "LOCAL-TEMP-TOKEN";
    /**
     * 本地session存活时间
     */
    int LOCAL_SESSION_LIVE_TIME = 1800;

    String GLOBAL_SESSION_ROLES = "AUTH-USER-ROLES";

    String GLOBAL_SESSION_LOGIN_INFO = "AUTH-USER-LOGIN-INFO";

    String X_GLOBAL_SESSION_KEY = "X-GLOBAL-SESSION";
}
