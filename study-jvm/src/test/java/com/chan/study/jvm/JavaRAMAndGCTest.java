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
     * VM args: -verbose:gc -Xms20M -Xmx=20M -Xmn10M -Xloggc:E:\StudyGCLog\testAllocation.log -XX:+PrintGCDetails -XX:SurvivorRatio=8
     */
    @Test
    public void testAllocation() {
        byte[] allocation, allocation2, allocation3, allocation4;
        allocation = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation4 = new byte[2 * _1MB];

    }

}
