package com.chan.study.feign.consumer.service;

import com.chan.study.feign.rpc.DemoApi;
import com.chan.study.feign.rpc.fail.FallDemoApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "product",fallback = FallDemoApi.class)
public interface DemoService extends DemoApi {
}
