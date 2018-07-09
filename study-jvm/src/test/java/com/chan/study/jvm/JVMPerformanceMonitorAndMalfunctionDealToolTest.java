package com.chan.study.jvm;

import org.junit.Test;

public class JVMPerformanceMonitorAndMalfunctionDealToolTest {
    /**
     * jps : 显示所有JAVA进程
     * jstat : 手机 HotSpot 虚拟机各方面的运行数据
     * jinfo : 显示虚拟机配置信息
     * jmap : 内存快照
     * jhat : 分析heapdump文件
     * jstack: 显示虚拟机线程快照
     */

    /**
     * jstat -
     * class   监视类装载、卸载数量、总空间以及类装载所耗费的时间
     * gc  监视Java堆状况，包括 Eden 区、两个 survivor 区、老年代、永久代等容量、已用空间、GC时间合计等等
     * gccapacity  与gc相同 但是 主要输出关注Java堆各个区域使用到的最大、最小空间
     * gcutil  监视
     * gccause 监视
     * gcnew   监视
     * gcnewcapactiy   监视
     * gcold   监视
     * gcoldcapacity   监视
     * compiler    监视
     * printcompilation    监视
     */


    /**
     * BTrace 测试
     * @throws InterruptedException
     */
    @Test
    public void BTraceTest() throws InterruptedException {
        while (true) {
            say(Math.random()+"");
            Thread.sleep(2000L);
        }
    }

    public void say(String str) {
        System.out.println(str);
    }
}
