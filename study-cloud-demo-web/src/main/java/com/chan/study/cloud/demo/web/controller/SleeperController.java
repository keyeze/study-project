package com.chan.study.cloud.demo.web.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sleep")
public class SleeperController {
    @RequestMapping("/{time}-second")
    public String sleep(@PathVariable("time") Long time) throws InterruptedException {
        Thread.sleep(time);
        return "OK";
    }
}
