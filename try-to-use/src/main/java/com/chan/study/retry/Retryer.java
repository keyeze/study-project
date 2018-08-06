package com.chan.study.retry;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class Retryer<T> {
    private List<Class<? extends Exception>> enableRetryExps;
    private EnableRetryResult<T> enableRetryResult;
    private StopStrategy stopStrategy;
    private WaitStrategy waitStrategy;


    public T execute(IService<T> service) throws RetryException {
        try {
            T result = tryDo(service);
        } catch (Exception e) {
            if (enableRetryExps.indexOf(e.getClass()) >= 0) {
            }
            for (Class<? extends Exception> enableRetryExpCls : enableRetryExps) {
                if (e.getClass().isAssignableFrom(enableRetryExpCls)) {
                    return tryDo(service);
                }
            }
            throw e;
        } finally {

        }
        return null;
    }

    private T tryDo(IService<T> service) {
        stopStrategy.deService();
        waitStrategy.doService();
        return service.doService();

    }


    private static class StopException extends RuntimeException {
    }

    private static class RetryException extends Exception {
    }

    public static void main(String[] args) throws ParseException, InterruptedException {
        while (true) {
            long endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-08-06 19:00:00").getTime();
            long nowTime = System.currentTimeMillis();
            long temp;
            long hour = (endTime - nowTime) / (1000 * 60 * 60);
            temp = (endTime - nowTime) % (1000 * 60 * 60);
            long min = temp / (1000 * 60);
            temp = (endTime - nowTime) % (1000 * 60);
            long second = temp / 1000;
            long mil = temp % 1000;
            NumberFormat nf = new DecimalFormat("00");
            NumberFormat nf2 = new DecimalFormat("00");
            System.out.println("距离下班还有:" + nf.format(hour) + ":" + nf.format(min) + ":" + nf.format(second) + "." + nf2.format(mil));
            Thread.sleep(1000);
        }
    }


}
