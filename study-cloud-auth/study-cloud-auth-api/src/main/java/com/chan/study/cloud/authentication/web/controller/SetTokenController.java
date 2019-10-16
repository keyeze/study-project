package com.chan.study.cloud.authentication.web.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/cross/domain")
public class SetTokenController {
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

    @RequestMapping("/token/script")
    public String script(@RequestParam String callback,@RequestParam String token) {
        String data = JSON.toJSONString(new ArrayList<String>() {{
            add("http://peer5/ssl-token.html");
        }});
        return SSL_SET_TOKEN_TEMPLATE
                .replaceAll("@@CALLBACK@@",callback)
                .replaceAll("@@DOMAIN_RECEIVERS@@",data)
                .replaceAll("@@TOKEN@@",token);
    }
}
