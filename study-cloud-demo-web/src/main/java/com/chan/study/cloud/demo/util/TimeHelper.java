package com.chan.study.cloud.demo.util;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TimeHelper {
    @Resource
    private SinglePointTimer singlePointTimer;

    public Map<String, String> timeMessages = new ConcurrentHashMap<>();

    //时间计时器
    public void countDown(Date targetTime, ListenerMessage listener) {
        countDown(targetTime, (long currentTime) -> listener != null, listener);
    }

    //时间计时器
    public String countDown(Date targetTime, SinglePointTimer.Condition listenerCondition, ListenerMessage listener) {
        String key = new SimpleDateFormat("yyyyMMddHHmmss").format(targetTime);
        return Optional.ofNullable(timeMessages.get(key)).orElseGet(() -> {
            singlePointTimer.pushTask(currentTime -> currentTime >= targetTime.getTime()
                    , currentTime -> timeMessages.put(key, Optional.ofNullable(timeMessages.get(key)).orElse("已超时").concat(",计时已结束"))
                    , listenerCondition
                    , (currentTime) -> timeMessages.put(key, listener.doIfPresence(currentTime))
            );
            return Thread.currentThread().getName() + "计时任务已经启动";
        });
    }

    @FunctionalInterface
    public interface ListenerMessage {
        String doIfPresence(long currentTime);
    }

}
