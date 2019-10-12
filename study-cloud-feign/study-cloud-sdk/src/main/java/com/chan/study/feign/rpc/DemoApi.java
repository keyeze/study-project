package com.chan.study.feign.rpc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/demo")
public interface DemoApi {
    @GetMapping("/test")
    String test();
}
