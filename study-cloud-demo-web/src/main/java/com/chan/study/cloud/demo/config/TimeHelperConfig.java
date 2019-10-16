package com.chan.study.cloud.demo.config;

import com.chan.study.cloud.util.SinglePointTimer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


@Configuration
@EnableAsync
public class TimeHelperConfig {

    @Bean
    @Primary
    public ExecutorService ctChanExecutor() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MICROSECONDS, new SynchronousQueue<>(), r -> new Thread(r) {{
            setName("CtChan:" + getName());
        }});
        return threadPoolExecutor;
    }

    @Bean
    public ExecutorService taskExecutor() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(20, 20, 0, TimeUnit.MICROSECONDS, new LinkedBlockingDeque<>(20), r -> new Thread(r) {{
            setName("TimeTask:" + getName());
        }});
        return threadPoolExecutor;
    }

    @Bean
    public SinglePointTimer singlePointTimer(@Autowired ExecutorService ctchanExecutor, ExecutorService taskExecutor) {
        SinglePointTimer singlePointTimer = new SinglePointTimer();
        ctchanExecutor.execute(singlePointTimer);
        singlePointTimer.setTaskExecutor(taskExecutor);
        return singlePointTimer;
    }
}
