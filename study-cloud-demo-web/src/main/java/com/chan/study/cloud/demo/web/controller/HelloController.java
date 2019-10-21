package com.chan.study.cloud.demo.web.controller;

import com.chan.study.cloud.authentication.consts.TokenConstant;
import jdk.nashorn.internal.parser.Token;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class HelloController {

    @RequestMapping("hello")
    public Object test(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        Optional.ofNullable(request.getHeaderNames()).ifPresent(item -> {
            Map<String, Object> temp = new HashMap<>();
            while (item.hasMoreElements()) {
                String key = item.nextElement();
                temp.put(key, request.getHeader(key));
                map.put("header",temp);
            }
        });
        Optional.ofNullable(request.getSession(false)).map(HttpSession::getAttributeNames).ifPresent(item -> {
            Map<String, Object> temp = new HashMap<>();
            while (item.hasMoreElements()) {
                String key = item.nextElement();
                temp.put(key, request.getSession().getAttribute(key));
                map.put("session", temp);
            }
        });
        return map;
    }
    @RequestMapping("test/hello")
    public Object test2() {
        return "hello-world";
    }
    @RequestMapping("test3/hello")
    public Object test3(HttpServletRequest request) {
        InfoBean info = (InfoBean)request.getSession().getAttribute("AGENT_SESSION");
        return info.getTest();
    }
}
