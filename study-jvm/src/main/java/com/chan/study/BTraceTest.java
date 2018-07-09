/*
package com.chan.study;

*/
/* BTrace Script Template *//*

import com.sun.btrace.*;

@BTrace
public class TracingScript {
    */
/* put your code here *//*

    @OnMethod(
            clazz="com.chan.study.jvm.JVMPerformanceMonitorAndMalfunctionDealToolTest",
            method="say",
            location=@Location(Kind.RETURN))
    public static void func(@Self com.chan.study.jvm.JVMPerformanceMonitorAndMalfunctionDealToolTest instance,
                            String  str ,@Return Void   result){
        println("I know it  said is :"+str+"'");
    }

}
*/
