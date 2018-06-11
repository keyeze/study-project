package com.chan.study.retry;

@FunctionalInterface
public interface WaitStrategy {
    void doService();
}
