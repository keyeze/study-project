package com.chan.study.jvm;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class OutOfMemoryErrorTest {
    /**
     * 制造堆内存溢出,并让JVM输出溢出时的快照信息
     * 环境变量配置:
     * VM Args:-Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
     *
     * @author Chenzuqiang
     */
    @Test
    public void HeapDumpOnOutOfMemoryError() {
        List<Object> list = new ArrayList<>();
        while (true) {
            list.add(new Object() {
                byte[] space = new byte[2 << 10];
            });
        }
    }

    /**
     * 制造虚拟机栈溢出BUG
     * VM Args:-Xss128k
     */
    @Test
    public void StackOverflowErrorExp1() {
        StackOverflowErrorExp1();
    }
    //todo InHomeTest:运行失败

    /**
     * 据说会造成虚拟机 OutOfMemoryError:unable to create new native thread,不要轻易尝试！！
     * VM Args:-Xss2M
     */
    @Test
    public void StackOverflowErrorExp2() {
        List<Thread> lists = new ArrayList<>();
        while (true) {
            lists.add(new Thread(() -> {
                while (true) {
                }
            }) {{
                start();
            }});
        }
    }
    //todo InHomeTest:java 8 下运行失败

    /**
     * 方法区或运行时常量池溢出,据说
     * VM Args： -XX:PermSize=10M -XX:MaxPermSize=10M   ---------- 1.7以前,
     */
    @Test
    public void OOMInRuntimeConstantPool() {
        List<String> list = new ArrayList<>();
        for (int value = Integer.MAX_VALUE; value > 0; value--) {
            list.add(String.valueOf(value++).intern());
        }
    }

    @Test
    public void differentInVer6AndVer7() {
        String temp = "我是对的";
        // there is cannot replace to "我是"+"对的"
        // you can see the .class
        String str = new StringBuilder().append("我是").append("对的").toString();
        System.out.println(str.intern() == str);
        String str2 = new StringBuilder().append("ja").append("va").toString();
        System.out.println(str2.intern() == str2);
    }


}
