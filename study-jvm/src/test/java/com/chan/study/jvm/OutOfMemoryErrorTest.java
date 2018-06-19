package com.chan.study.jvm;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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
//        String temp = "我是对的";
        // there is cannot replace to "我是"+"对的"
        // you can see the .class
        String str = new StringBuilder().append("我是").append("对的").toString();
        System.out.println(str.intern() == str);
        String str2 = new StringBuilder().append("ja").append("va").toString();
        System.out.println(str2.intern() == str2);
    }

    /**
     * 借助CGLib使方法区出现内存溢出的异常,-----1.7之前
     * VM Args: -XX:PermSize=10M -XX:MaxPermSize=10M
     */
    @Test
    public void OOMInJavaMethodArea() {
        while (true) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(this.getClass());
            enhancer.setUseCache(false);
            enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> methodProxy.invokeSuper(o, objects));
            enhancer.create();
        }
    }

    /**
     * 本地直接内存溢出
     * VM Args: -Xmx20M -XX:MaxDirectMemorySize=10M
     */
    @Test
    public void OOMInNativeDirectMemory() throws IllegalAccessException {
        Field unsafField = Unsafe.class.getDeclaredFields()[0];
        unsafField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafField.get(null);
        while (true) {
            unsafe.allocateMemory(1024 * 1024);
        }
    }

    /**
     * 检测循环依赖是否被回收
     * VM Args:-Xloggc:C:\Users\Administrator\Desktop\1.txt -XX:+PrintGCDetails
     *
     * 垃圾收集齐参数一览:
     * UseSerialGC
     * UseParNewGC
     * UseConcMarkSweepGC
     * UseParallelGC
     * UseParallelOldGC
     * SurvivorRatio                    年轻带中 Eden:Survivor比值,default=8
     * PretenureSizeThreshold           晋升到老年代的对象大小限制,如果超过直接进入老年代,否则执行 MaxTenuringThreshold 逻辑
     * MaxTenuringThreshold             晋升到老年代的对象年龄限制,如果大于该年龄,则进入老年代
     * UseAdaptiveSizePolicy            让虚拟机自己动态分配整层管理 PretenureSizeThreshold 和 MaxTenuringThreshold
     * HandlePromotionFailure           是否在老年代不足时使用新生代来补充
     * ParallelGCThreads                ParallelGC 并行回收的线程数
     * GCTimeRadio                      ParallelGC CPU吞吐率 1/(1+n) default = 99(即 回收占用CPU时间=1%)
     * CMSInitiatingOccupancyFraction   CMS 进行老年代垃圾回收的临界值 ,default = 68%
     * UseCMSCompactAtFullCollection    CMS 标记-清除完成后是否进行 压缩 操作
     * CMSFullGCsBeforeCompaction       CMS 在 n 次回收以后执行一次 压缩 操作
     */
    @Test
    public void GCTest() {
        Object obj = new Object() {
            private Object obj = null;
            private static final int _1MB = 1024 * 1024;
            private byte[] space = new byte[_1MB * 20];

            {
                Object _this = this;
                obj = new Object() {
                    private Object obj = null;
                    private byte[] space = new byte[_1MB * 20];

                    {
                        obj = _this;
                    }
                };
            }
        };
        obj = null;
        System.gc();
        while (true){}
    }

    private static FinalizeEscapeGC finalizeEscapeGC;

    private static class FinalizeEscapeGC {
        @Override
        protected void finalize() throws Throwable {
            super.finalize();
            System.out.println("save myself");
            finalizeEscapeGC = this;

        }
    }

    @Test

    public void saveSelf() throws InterruptedException {
        finalizeEscapeGC = new FinalizeEscapeGC();
        finalizeEscapeGC = null;
        int i = 0;
        Executors.newFixedThreadPool(1).execute(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    if (System.currentTimeMillis() % 4 == 0) {
                        System.out.println("set finalizeEscapeGC is null");
                        finalizeEscapeGC = null;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        while (true) {
            Thread.sleep(1000L);
            System.out.println(i);
            if (i++ % 5 == 0) {
                System.out.println("start gc");
                System.gc();
            }

        }

    }


}
