package com.chan.study.retry;

@FunctionalInterface
public interface StopStrategy {
    boolean deService();
}
