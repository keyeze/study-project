package com.chan.study.cloud.demo.web.controller;

import com.chan.study.cloud.demo.util.SinglePointTimer;
import com.chan.study.cloud.demo.util.TimeHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.Date;

@Profile("peer1")
@RestController
@RequestMapping("/time-helper")
@Slf4j
public class DemoPeer1Controller {
    public static final long SECOND_UNIT = 1000;
    public static final long MIN_UNIT = 1000 * 60;
    public static final long HOUR_UNIT = 1000 * 60 * 60;
    public static final long DAY_UNIT = 1000 * 60 * 60 * 24;

    @Resource
    private TimeHelper timeHelper;

    @GetMapping("/say/hello/{what}")
    public String sayHelloStrPeer1(@PathVariable("what") String what) {
        return "Hello " + what + " , Peer1 !";
    }

    @GetMapping("/timing/{pattern}/{time}")
    @ResponseBody
    public String timing(@PathVariable("time") String time, @PathVariable("pattern") String pattern) throws ParseException {
        final long nowTime = System.currentTimeMillis();
        final Date targetTime = new SimpleDateFormat(pattern).parse(time);
        return timeHelper.countDown(targetTime
                , currentTime -> (((nowTime - currentTime) / SinglePointTimer.DEFAULT_STEP) % 20) == 0
                , currentTime -> {
                    String timingStr = getTimingStr(targetTime.getTime(), currentTime);
                    System.out.println(Thread.currentThread().getName() + "距离目标时间 : " + timingStr);
                    try {
                        return Thread.currentThread().getName() + "距离目标时间 : " + timingStr;
                    } catch (DateTimeException e) {
                        return new SimpleDateFormat("HH:mm:ss").format(new Date(currentTime)) + " 任务已完成";
                    }
                });
    }

    public static String getTimingStr(long targetTime, long currentTime) {
        long temp = targetTime - currentTime;
        int days = (int) (temp / DAY_UNIT);
        temp = targetTime - currentTime;
        int hour = (int) (temp / HOUR_UNIT);
        temp = temp % HOUR_UNIT;
        int min = (int) (temp / MIN_UNIT);
        temp = temp % MIN_UNIT;
        int secoud = (int) (temp / SECOND_UNIT);
        return (days == 0 ? "" : days + "天 ") + hour + ":" + min + ":" + secoud;
    }
}
