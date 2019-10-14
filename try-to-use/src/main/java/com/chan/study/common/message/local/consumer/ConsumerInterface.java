package com.chan.study.common.message.local.consumer;

import com.chan.study.common.message.IMessage;
import com.chan.study.common.message.consumer.ICService;

/**
 * 被动方消息确认
 *
 * @param <T> 返回实体类型
 * @param <U> 消息唯一标识
 */
public interface ConsumerInterface<T, U, V extends IMessage<T, U>> {
    /**
     * 消息确认
     *
     * @param receiveMessage 接收消息
     * @param service        服务逻辑
     * @param <W>            返回类型
     */
    <W extends ResultReferee> void ackMessage(V receiveMessage, ICService<T, U, V, W> service);

    /**
     * 查询消息主动方消息状态
     */
    boolean queryMessageStatus();
}
