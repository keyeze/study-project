package com.chan.study.cloud.authentication.domain;

import com.chan.study.cloud.authentication.warm.Dynamic;
import lombok.Data;

import java.util.List;

/**
 * 用户登录态信息
 */
@Data
public class LoginInfo {
    private String uuid;
    private String birth;
    @Dynamic
    private List<String> roles;
}
