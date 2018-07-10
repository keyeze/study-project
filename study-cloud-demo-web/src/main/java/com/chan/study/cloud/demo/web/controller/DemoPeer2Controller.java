package com.chan.study.cloud.demo.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("peer2")
@RestController
@RequestMapping("/demo")
@Slf4j
public class DemoPeer2Controller {

    @GetMapping("say/hello/{what}")
    public String sayHelloStrPeer2(@PathVariable("what") String what) {
        return "Hello " + what + " , Peer2 !";
    }

}
