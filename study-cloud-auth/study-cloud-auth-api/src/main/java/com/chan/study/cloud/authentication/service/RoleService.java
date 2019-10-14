package com.chan.study.cloud.authentication.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {
    public List<String> getRolesByUuid(){
        //todo 获取uuid相关的roles
        return new ArrayList<String>(){{
            add("wx:1");
            add("wx:2");
            add("business:test");
        }} ;
    }

    public void addRolesByUuid(String uuid, List<String> roles) {
        //todo 更新uuid相关的roles
    }
}
