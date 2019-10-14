/*
package com.chan.study.common.message.serv.producer;

import com.chan.study.common.message.IMessage;
import com.chan.study.common.message.InTransaction;
import com.chan.study.common.message.local.producer.ProducerInterface;
import com.chan.study.common.message.producer.IPService;

*/
/**
 * 可靠消息最终一致性方案--本地消息服务
 *
 * @author CtChan
 *//*

public abstract class PServMessageService<
        T,
        U,
        V extends IPreMessage<T, U, TV>,
        X extends IServPService<T, U, V, TV>,
        TV extends IMessage<T, U>
        >
        implements ProducerServInterface<T, U, V, X, TV> {

    */
/**
     * 发送消息在业务操作完成之后。
     *
     * @param service
     * @return
     *//*

    @Override
    @InTransaction
    public void sendMsg(X service) {
        //
        V iPreMessage = service.execute();
        //业务预发消息操作
        sendMessage(iPreMessage);
        //继续执行业务处理
        TV successMessage = iPreMessage.execute();
        //消息存储
        sendResult(successMessage);
    }

    protected abstract void sendResult(TV resultMessage);

    */
/**
     * 查询本地消息类型消息状态
     *
     * @param key
     *//*

    @Override
    public boolean queryMessageStatus(U key) {
        return false;
    }

    */
/**
     * 消息查询（查询消息是否发送成功，否则重新发送一个恢复消息至MQ中）
     *
     * @param key
     * @return 消息对象
     *//*

    @Override
    public V queryMessage(U key) {
        return null;
    }

    */
/**
     * 普通 MQ 消息确认流程
     *
     * @param iMessage
     *//*

    abstract void sendSuccess(V iMessage);


    */
/**
     * 发送消息
     *
     * @param iMessage
     *//*

    abstract void sendMessage(V iMessage);

    */
/**
     * 本地消息保存
     *
     * @param key
     * @param iMessage
     *//*

    abstract void saveLocal(U key, V iMessage);


}
*/
