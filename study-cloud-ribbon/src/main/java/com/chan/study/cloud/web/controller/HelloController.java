package com.chan.study.cloud.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@RequestMapping("/")
public class HelloController {
    @Resource
    private RestTemplate restTemplate;

    @GetMapping("hello")
    public String hello() {
        return restTemplate.getForEntity("http://DEMO-WEB/demo/say/hello/world", String.class).getBody();
    }
}
