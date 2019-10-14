package com.chan.study.cloud.authentication.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {
    public List<String> getRolesByUuid(String uuid) {
        //todo 获取uuid相关的roles
        return new ArrayList<String>() {{
            add("wx:1");
            add("wx:2");
            add("business:test");
        }};
    }

    @Transactional
    public void changeRolesByUuid(String uuid, List<String> roles) {
        //todo 更新uuid相关的roles
        List<String> removeRoles = roles.stream().filter(item -> item.startsWith("-")).map(String::trim).collect(Collectors.toList());
        List<String> addRoles = roles.stream().filter(item -> item.startsWith("+")).map(String::trim).collect(Collectors.toList());
        removeRoles.forEach(item -> this.removeRoleIfPresent(uuid, item));
        addRoles.forEach(item -> this.addRoleIfAbsent(uuid, item));
    }


    @Transactional
    public void addRoleIfAbsent(String uuid,String roleKey){
        //todo 如果不存在role则新增
    }

    @Transactional
    public void removeRoleIfPresent(String uuid,String roleKey){
        //todo 如果存在role则删除
    }
}
