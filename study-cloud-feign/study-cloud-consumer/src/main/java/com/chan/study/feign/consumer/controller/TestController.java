package com.chan.study.feign.consumer.controller;

import com.chan.study.feign.consumer.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("test")
@RestController
public class TestController {

    @Autowired
    DemoService demoService;

    @GetMapping("hello")
    public String hello() {
        return "hello," + demoService.test();

    }
}
