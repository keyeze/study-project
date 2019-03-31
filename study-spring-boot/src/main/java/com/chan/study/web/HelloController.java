package com.chan.study.web;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
/**
 * {@link EnableAutoConfiguration} will add the 'Tomcat' and 'Spring MVC'
 */
public class HelloController {
    @GetMapping("/")
    public String hello() {
        return "hello";
    }
}
