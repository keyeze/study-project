package com.chan.study.cloud.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloController {

    @RequestMapping("hello")
    public Object test(HttpServletRequest request){
        Enumeration<String> result = request.getHeaderNames();
        Map<String,Object> map = new HashMap<>();
        while (result.hasMoreElements()) {
            String key = result.nextElement();
            map.put(key,request.getHeader(key));
        }
        map.put("test-key",request.getSession().getAttribute("test-key"));
        return map;
    }
}
