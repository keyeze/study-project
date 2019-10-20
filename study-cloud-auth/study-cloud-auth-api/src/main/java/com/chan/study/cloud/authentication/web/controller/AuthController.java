package com.chan.study.cloud.authentication.web.controller;

import com.chan.study.cloud.authentication.domain.ChangeRolesReq;
import com.chan.study.cloud.authentication.domain.LoginInfo;
import com.chan.study.cloud.authentication.service.AuthApi;
import com.chan.study.cloud.authentication.service.AuthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
    public LoginInfo verifyAndGetUserInfo(@RequestBody String token) {
        return authService.getUserInfoByToken(token);
    }

    @Override
    @PostMapping("/change-roles")
    public String changeRolesForUserByToken(@RequestBody ChangeRolesReq changeRoles){
        authService.changeRolesByToken(changeRoles.getToken(),changeRoles.getRoles());
        return "success";
    }

    @GetMapping("/test-token")
    public String changeRolesForUserByToken(@RequestParam String uuid){
        return authService.createToken(uuid);
    }
}
