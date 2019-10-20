package com.chan.study.cloud.authentication.domain;

import com.chan.study.cloud.authentication.warm.Dynamic;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户登录态信息
 */
@Data
public class LoginInfo implements Serializable {
    private String uuid;
    private String birth;
    @Dynamic
    private List<String> roles;
}
