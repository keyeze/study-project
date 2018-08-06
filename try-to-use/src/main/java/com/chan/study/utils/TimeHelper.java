package com.chan.study.utils;

import javax.annotation.Resource;
import java.util.Date;

public class TimeHelper {

    @Resource
    private SinglePointTimer singlePointTimer;

    //时间计时器
    public void countDown(Date targetTime, SinglePointTimer.Listener listener) {
        countDown(targetTime, (long currentTime) -> listener != null, listener);
    }

    //时间计时器
    public void countDown(Date targetTime, SinglePointTimer.Condition listenerCondition, SinglePointTimer.Listener listener) {
        singlePointTimer.pushTask(currentTime -> currentTime >= targetTime.getTime(), listenerCondition, listener);
    }

}
