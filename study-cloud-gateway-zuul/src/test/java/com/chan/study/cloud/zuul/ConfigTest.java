package com.chan.study.cloud.zuul;

import com.alibaba.fastjson.JSON;
import com.chan.study.cloud.zuul.config.GatewayFilterConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class ConfigTest {
    @Autowired
    private GatewayFilterConfig config;

    @Test
    public void isAutomaticIntoConfig() {
        log.info("test-result:{}", JSON.toJSONString(config));

    }
}
