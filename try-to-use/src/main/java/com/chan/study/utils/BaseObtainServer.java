package com.chan.study.utils;

public abstract class BaseObtainServer<T, V> implements ObtainServer<T, V> {
    @Override
    public V apply(T key) {
        V result = query(key);
        if (!isEmpty(result)) {
            return result;
        }
        result = secordQuery(key);
        if (!isEmpty(result)) {
            return result;
        }
        throw new RuntimeException("无法获取值");
    }

    public abstract V secordQuery(T key);

    public abstract V query(T key);

    public abstract boolean isEmpty(V result);
}
