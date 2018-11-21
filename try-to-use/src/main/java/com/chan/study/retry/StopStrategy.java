package com.chan.study.retry;

@FunctionalInterface
public interface StopStrategy<T> {
    boolean judgment(T result);
}
