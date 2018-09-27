package com.chan.study.utils;

public interface ObtainServer<T, V> {
    V apply(T key);
}
