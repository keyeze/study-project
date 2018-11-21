package com.chan.study;


import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Arrays;

public class StartThreadsTest {
    public static void main(String[] args) {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        Arrays.stream(threadMXBean.dumpAllThreads(false, false)).forEach(item -> {
            System.out.println(item.getThreadId() + "..." + item.getThreadName());
        });
    }
}
