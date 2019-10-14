package com.chan.study.common.message.producer;

import com.chan.study.common.message.IMessage;

/**
 * 业务操作
 *
 * @param <T> 业务实体类型
 * @author CtChan
 */
@FunctionalInterface
public interface IPService<T, U, V extends IMessage<T, U>> {
    /**
     * 返回消息类型
     *
     * @return
     */
    V execute();
}
