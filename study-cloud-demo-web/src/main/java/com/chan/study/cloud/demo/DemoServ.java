package com.chan.study.cloud.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
@Slf4j
public class DemoServ {
    int i = 1000;

    @RequestMapping("/return-body")
    public String returnBody(@RequestBody String body) throws InterruptedException {
        Thread.sleep(i);
        log.info("sleep" + i);
        i += 1000;
        return body;
    }

}
