package com.chan.study.common.message.serv.midserv;

import com.chan.study.common.message.IMessage;
import com.chan.study.common.message.serv.producer.IPreMessage;

/**
 * 消息服务子系统
 *
 * @param <T>
 * @param <U>
 * @param <V>
 * @param <TV>
 */
public interface MidMessageService<T, U, V extends IPreMessage<T, U, TV>, TV extends IMessage<T, U>> {
    /**
     * 接收与发送消息
     *
     * @param preMessage 预发送消息
     */
    void savePreMessage(V preMessage);

    /**
     * 确认并发送消息
     */
    void confirmAndSendMessageToCunsumer(V successMessage);

    /**
     * 发送预发消息
     */
    void queryPre(U Key);

    /**
     * 发送预发消息
     */
    void sendPreMessage(U Key);


}
