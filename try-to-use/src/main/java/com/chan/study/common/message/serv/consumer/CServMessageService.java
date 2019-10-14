package com.chan.study.common.message.serv.consumer;

import com.chan.study.common.message.IMessage;
import com.chan.study.common.message.InTransaction;
import com.chan.study.common.message.consumer.ICService;
import com.chan.study.common.message.local.consumer.ConsumerInterface;
import com.chan.study.common.message.local.consumer.ResultReferee;

/**
 * 可靠消息最终一致性方案--本地消息服务
 * 业务返回类型
 *
 * @param <V> 消息类型
 */
public abstract class CServMessageService<T, U, V extends IMessage<T, U>> implements ConsumerInterface<T, U, V> {


    /**
     * 消息确认
     *
     * @param service 服务逻辑
     * @param <W>     处理结果类型
     */
    @Override
    @InTransaction
    public <W extends ResultReferee> void ackMessage(V receiveMessage, ICService<T, U, V, W> service) {
        //业务操作(可以设置为自动接收)
        try {
            //幂等性处理
            idempotent(receiveMessage);
            //业务操作
            W result = service.execute(receiveMessage);
            //向主动发消息确认
            result.judgeExe(() -> confirmMessage(receiveMessage.getKey()));
        } finally {
            if (receiveMessage != null) {
                //将'待发送'转为'已确认'
                sendSuccess(receiveMessage);
            }
        }
    }

    /**
     * 幂等性处理
     *
     * @param receiveMessage 消息体
     */
    protected abstract void idempotent(V receiveMessage);

    /**
     * 向MQ发送接收成功通知,(如果为自动接收可以不发送)
     *
     * @param receiveMessage 消息体
     */
    protected abstract void sendSuccess(V receiveMessage);

    /**
     * 向主动发消息确认
     *
     * @param key 消息体
     */
    protected abstract void confirmMessage(U key);

    /**
     * 查询消息主动方消息状态
     */
    @Override
    public boolean queryMessageStatus() {
        return false;
    }

}
