package com.chan.study.retry;

@FunctionalInterface
public interface IService<T> {
    T doService();
}
