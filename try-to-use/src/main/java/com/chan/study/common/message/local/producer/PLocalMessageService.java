/*
package com.chan.study.common.message.local.producer;

import com.chan.study.common.message.IMessage;
import com.chan.study.common.message.InTransaction;
import com.chan.study.common.message.producer.IPService;

*/
/**
 * 可靠消息最终一致性方案--本地消息服务
 *
 * @author CtChan
 *//*

public abstract class PLocalMessageService<T, U, V extends IMessage<T, U>> implements ProducerInterface<T, U, V> {

    */
/**
     * 发送消息在业务操作完成之后。
     *
     * @param service
     * @return
     *//*

    @Override
    @InTransaction
    public void sendMsg(IPService<T, U, V> service) {
        //业务操作
        V iMessage = null;
        try {
            //业务操作
            iMessage = service.execute();
            //发送代确认消息
            sendMessage(iMessage);
            //消息存储
            saveLocal(iMessage.getKey(), iMessage);
        } finally {
            if (iMessage != null) {
                //将'待确认'转为'待发送'
                sendSuccess(iMessage);
            }
        }
    }

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
