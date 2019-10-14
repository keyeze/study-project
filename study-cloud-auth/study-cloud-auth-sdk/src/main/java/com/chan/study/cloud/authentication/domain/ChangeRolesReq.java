package com.chan.study.cloud.authentication.domain;

import lombok.Data;

import java.util.List;

@Data
public class ChangeRolesReq {
    private String token;
    private List<String> roles;
}
