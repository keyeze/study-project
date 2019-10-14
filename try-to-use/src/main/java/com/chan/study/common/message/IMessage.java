package com.chan.study.common.message;

/**
 * 消息实体类
 *
 * @param <T>
 */
public interface IMessage<T, U> {
    /**
     * 提供唯一组件Key
     */
    U getKey();
}
