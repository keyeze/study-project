package com.chan.study.cloud.authentication.web.controller;

import com.chan.study.cloud.authentication.domain.ChangeRolesReq;
import com.chan.study.cloud.authentication.domain.LoginSessionDto;
import com.chan.study.cloud.authentication.domain.VerifyTokenReq;
import com.chan.study.cloud.authentication.service.AuthApi;
import com.chan.study.cloud.authentication.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 鉴权中心接口
 * @author CtChan
 */
@RestController
public class AuthController implements AuthApi {
    @Resource
    private AuthService authService;

    @Override
    @PostMapping("/v2/verify")
    public LoginSessionDto verifyAndGetUserInfo(@RequestBody VerifyTokenReq verifyToken) {
        return authService.getUserInfoByToken(verifyToken.getToken());
    }

    @Override
    @PostMapping("/change-roles")
    public String changeRolesForUserByToken(@RequestBody ChangeRolesReq changeRoles){
        authService.changeRolesByToken(changeRoles.getToken(),changeRoles.getRoles());
        return "success";
    }
}
