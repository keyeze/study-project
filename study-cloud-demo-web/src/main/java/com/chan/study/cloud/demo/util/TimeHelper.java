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

    /**
     * 时间计时任务
     *
     * @param targetTime        目标时间
     * @param listenerCondition 条件监听
     * @param listener          监听执行方法
     *
     * @return
     */

    public String countDown(Date targetTime, SinglePointTimer.Condition listenerCondition, ListenerMessage listener) {
        String key = new SimpleDateFormat("yyyyMMddHHmmss").format(targetTime);
        return Optional.ofNullable(timeMessages.get(key)).orElseGet(() -> {
            singlePointTimer.pushTask(
                    //结束条件
                    currentTime -> currentTime >= targetTime.getTime()
                    //结束执行方法
                    , currentTime -> timeMessages.put(key, Optional.ofNullable(timeMessages.get(key)).orElse("已超时").concat(",计时已结束"))
                    //计时监听条件
                    , listenerCondition
                    //监听执行方法
                    , (currentTime) -> timeMessages.put(key, listener.doIfPresence(currentTime))
            );
            return Thread.currentThread().getName() + "计时任务已经启动";
        });
    }


    @FunctionalInterface
    public interface ListenerMessage {
        /**
         * 如果为真就执行
         *
         * @param currentTime 当前时间
         *
         * @return
         */
        String doIfPresence(long currentTime);
    }

}
