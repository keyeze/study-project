package com.chan.study.common.message.serv.producer;

import com.chan.study.common.message.IMessage;
import com.chan.study.common.message.producer.IPService;

/**
 * 主动方消息发送
 *
 * @param <T> 返回实体类型
 * @param <U> 消息唯一标识
 */
public interface ProducerServInterface<
        T,
        U,
        V extends IPreMessage<T, U, TV>,
        X extends IServPService<T, U, V, TV>,
        TV extends IMessage<T, U>
        > {

    /**
     * 发送消息在业务操作完成之后。
     *
     * @return
     */

    void sendMsg(X service);

}
