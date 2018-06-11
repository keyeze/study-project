package com.chan.study.retry;

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

    public static void main(String[] args) {

    }
}
