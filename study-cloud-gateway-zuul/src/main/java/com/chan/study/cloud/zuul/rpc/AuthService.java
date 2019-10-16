package com.chan.study.cloud.zuul.rpc;

import com.chan.study.cloud.authentication.consts.AuthServiceContstant;
import com.chan.study.cloud.authentication.service.AuthApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = AuthServiceContstant.SERVICE_NAME)
public interface AuthService extends AuthApi {
}
