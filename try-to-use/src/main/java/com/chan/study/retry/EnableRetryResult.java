package com.chan.study.retry;

public interface EnableRetryResult<T> {
    public boolean apply(T result);
}
