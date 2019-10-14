package com.chan.study.cloud.web.controller;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@Controller
@RequestMapping("jsonp")
public class JsonpDemoController {

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "test")
    public DemoBean main() {
        return new DemoBean(){{
            setName("name");
            setText("text");
        }};
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "login")
    public String loginAndNew(HttpServletRequest request, HttpServletResponse response) {
        String login = "JWT_" + System.currentTimeMillis();
        response.addCookie(new Cookie("login",login));
        return "localStorage.login=\"" + login + "\";sessionStorage.login=\"" + login + "\"";
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "login/set")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        String login = "";
        for (Cookie cookie : request.getCookies()) {
            if ("login".equals(cookie.getName())) {
                login = cookie.getValue();
                break;
            }
        }
        return "localStorage.login=\"" + login + "\";sessionStorage.login=\"" + login + "\"";
    }

    private static final String SSL_SET_TOKEN_TEMPLATE =
            "@@CALLBACK@@(function(){" +
                "let json = @@DOMAIN_RECEIVERS@@;" +
                "let i = 0;" +
                "console.log('start set token to local storage ...');" +
                "json.forEach(item =>{" +
                    "let frameItem = document.createElement('iframe');" +
                    "let j = i++;" +
                    "frameItem.onload=function(){" +
                        "console.log('message to '+item);" +
                        "window.frames[j].postMessage('@@TOKEN@@','*');" +
                    "};" +
                    "frameItem.src = item;" +
                    "frameItem.hidden='hidden';" +
                    "document.body.append(frameItem);" +
                "});" +
            "})";

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "script")
    public String script(@RequestParam String callback,HttpServletResponse response) {
        String login = "JWT_" + System.currentTimeMillis();
        response.addCookie(new Cookie("login",login));
        String data = JSON.toJSONString(new ArrayList<String>() {{
            add("http://peer5/ssl-token.html");
            add("http://peer6/ssl-token.html");
            add("http://peer7/ssl-token.html");
            add("http://peer8/ssl-token.html");
        }});
        String token = " 小明同学 ";
        return SSL_SET_TOKEN_TEMPLATE
                .replaceAll("@@CALLBACK@@",callback)
                .replaceAll("@@DOMAIN_RECEIVERS@@",data)
                .replaceAll("@@TOKEN@@",token);

    }

    @Data
    public static class DemoBean {
        private String name;
        private String text;
    }
}
