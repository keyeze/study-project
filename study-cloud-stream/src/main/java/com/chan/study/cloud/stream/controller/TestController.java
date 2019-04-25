package com.chan.study.cloud.stream.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author CtChan
 */
@RestController
public class TestController {
    @RequestMapping("/hi")
    public String hi() {
        return "Hello World!";
    }
}
