package com.chan.study.jvm;

import org.junit.Test;

public class JavaRAMAndGCTest {
    /**
     * {@link OutOfMemoryErrorTest#GCTest}
     */
    public void GCTest() {
    }

    public static final Integer _1MB = 1024 * 1024;

    /**
     * VM args: -verbose:gc -Xms20M -Xmx20M -Xmn10M -Xloggc:E:\StudyGCLog\testAllocation.log -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+UseConcMarkSweepGC
     */
    @Test
    public void testAllocation() {
        byte[] allocation, allocation2, allocation3, allocation4;
        allocation = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation4 = new byte[4 * _1MB];
    }

    /**
     * VM args:-verbose:gc -Xms20M -Xmx20M -Xmn10M -Xloggc:E:\StudyGCLog\testPretenureSizeThreshold.log -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+UseConcMarkSweepGC -XX:PretenureSizeThreshold=1M
     * PretenureSizeThreshold 只对 Serial 和 ParNew 管用 ,1.8 默认收集器为 Parallel Scavenger + Parallel Old 不起作用.
     */
    @Test
    public void testPretenureSizeThreshold() {
        System.gc();
        byte[] allocation, allocation2, allocation3, allocation4;
        allocation = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation4 = new byte[4 * _1MB];
    }

    /**
     * VM args: -verbose:gc -Xms20M -Xmx20M -Xmn10M -Xloggc:E:\StudyGCLog\testTenuringThreshold.log -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=1 -XX:+PrintTenuringDistribution
     */
    @Test
    public void testTenuringThreshold() {
        System.gc();
        byte[] allocation, allocation2;
        allocation = new byte[_1MB / 4];
        for (int i = 14; i > 0; i--) {
            allocation2 = new byte[3 * _1MB];
        }
        allocation2 = null;
    }

    /**
     * VM args: -verbose:gc -Xms20M -Xmx20M -Xmn10M -Xloggc:E:\StudyGCLog\testTenuringThresholdForMomentMemoryMoreThanHalfOfSurvivor.log -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=1 -XX:+PrintTenuringDistribution
     * 当进入或者即将进入 Survivor的对象空间大于空间一半时,所有与该年龄相同或者大的对象,提前进入老年区
     */
    @Test
    public void testTenuringThresholdForMomentMemoryMoreThanHalfOfSurvivor() {
        System.gc();
        byte[] allocation, allocation2;
        allocation = new byte[_1MB / 4];
        for (int i = 15; i > 0; i--) {
            allocation2 = new byte[3 * _1MB];
        }
        allocation2 = null;

    }

    /**
     * VM args: -verbose:gc -Xms20M -Xmx20M -Xmn10M -Xloggc:E:\StudyGCLog\testTenuringThresholdForMomentMemoryLessThanHalfOfSurvivor.log -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=1 -XX:+PrintTenuringDistribution
     * 当进入或者即将进入 Survivor的对象空间大于空间一半时,所有与该年龄相同或者大的对象,提前进入老年区
     */
    @Test
    public void testTenuringThresholdForMomentMemoryLessThanHalfOfSurvivor() throws InterruptedException {
        System.gc();
        byte[] allocation, allocation2;
        allocation = new byte[_1MB / 4];
        while (true) {
            allocation2 = new byte[3 * _1MB / 5];
            Thread.sleep(1000);
        }

    }

    /**
     * 不懂...略过先
     * VM args: -verbose:gc -Xms20M -Xmx20M -Xmn10M -Xloggc:E:\StudyGCLog\testHandlePromotion.log -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:-HandlePromotionFailure
     */
    @Test
    public void testHandlePromotion() {
        byte[] allocation1, allocation2, allocation3, allocation4, allocation5, allocation6, allocation7;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation1 = null;
        allocation4 = new byte[2 * _1MB];
        allocation5 = new byte[2 * _1MB];
        allocation6 = new byte[2 * _1MB];
        allocation4 = null;
        allocation5 = null;
        allocation6 = null;
        allocation7 = new byte[2 * _1MB];
    }
}
