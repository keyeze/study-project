package com.chan.study.feign.rpc.fail;

import com.chan.study.feign.rpc.DemoApi;
import org.springframework.stereotype.Component;

@Component
public class FallDemoApi implements DemoApi {
    @Override
    public String test() {
        return "fail";
    }
}
