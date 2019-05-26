package com.chan.study.cloud.demo.web.controller;

import com.chan.study.cloud.demo.util.HttpClientUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("demo")
public class DemoController {
    @GetMapping("v2/go")
    public String go() {
        return HttpClientUtil.get("https://www.baidu.com/s?ie=utf-8&wd=jmeter-test");
    }
}
