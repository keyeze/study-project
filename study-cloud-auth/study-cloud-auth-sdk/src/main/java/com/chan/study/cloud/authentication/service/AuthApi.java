package com.chan.study.cloud.authentication.service;

import com.chan.study.cloud.authentication.domain.ChangeRolesReq;
import com.chan.study.cloud.authentication.domain.LoginSessionDto;
import com.chan.study.cloud.authentication.domain.VerifyTokenReq;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthApi {
    @PostMapping("/v2/verify")
    LoginSessionDto verifyAndGetUserInfo(@RequestBody VerifyTokenReq verifyToken);

    @PostMapping("/change-roles")
    String changeRolesForUserByToken(@RequestBody ChangeRolesReq changeRoles);
}
