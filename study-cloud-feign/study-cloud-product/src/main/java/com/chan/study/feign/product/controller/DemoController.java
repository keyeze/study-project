package com.chan.study.feign.product.controller;

import com.chan.study.feign.rpc.DemoApi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController implements DemoApi {
    @Override
    public String test() {
        return "OK";
    }
}
