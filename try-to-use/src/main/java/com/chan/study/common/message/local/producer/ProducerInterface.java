package com.chan.study.common.message.local.producer;

import com.chan.study.common.message.IMessage;
import com.chan.study.common.message.producer.IPService;

/**
 * 主动方消息发送
 *
 * @param <T> 返回实体类型
 * @param <U> 消息唯一标识
 */
public interface ProducerInterface<T, U, V extends IMessage<T, U>, X extends IPService<T, U, V>> {

    /**
     * 发送消息在业务操作完成之后。
     *
     * @return
     */

    void sendMsg(X service);

    /**
     * 查询本地消息类型消息状态
     */
    boolean queryMessageStatus(U key);

    /**
     * 消息查询（查询消息是否发送成功，否则重新发送一个恢复消息至MQ中）
     *
     * @return 消息对象
     */
    V queryMessage(U key);
}
