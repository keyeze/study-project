package com.chan.study.cloud.demo.web.controller;

import com.chan.study.cloud.demo.util.HttpClientUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("demo")
public class DemoController {
    @GetMapping("v2/go")
    public String go() {
        return HttpClientUtil.get("https://www.baidu.com/s?ie=utf-8&wd=jmeter-test");
    }

    @GetMapping("{p}/{service}")
    public String go(@PathVariable String service, @PathVariable String p,String url) {
        return HttpClientUtil.get(p+"://"+service+"/"+url);
    }

    public static void main(String[] args) throws IOException {
        HttpPost httpPost = new HttpPost("http://localhost:8170/authentication/authorize_url");
        HttpUriRequest result = RequestBuilder.post("http://localhost:8170/authentication/authorize_url")
                .addHeader("Content-Type","application/json")
                .addHeader("Connection","Keep-Alive")
                .setEntity(new StringEntity("{ \"wx_appid\":\"wxa4e0b505175d0163\", \"redirect_url\":\"http://10.80.245.26:8170/authentication/test\", \"is_silent_auth\":false }"))
                .build();
        HttpEntity response = HttpClients.createDefault().execute(result).getEntity();
        System.out.println("contentLength :" + response.getContentLength());
        System.out.println("contentEncoding :" + response.getContentEncoding());
        System.out.println("contentType :" + response.getContentType());
        System.out.println("isChunked :" + response.isChunked());
        System.out.println("isStreaming :" + response.isStreaming());
        System.out.println("isRepeatable :" + response.isRepeatable());

    }
    static void hello(){
        System.out.println("hello");
    }
}
