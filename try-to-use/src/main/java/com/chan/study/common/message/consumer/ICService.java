package com.chan.study.common.message.consumer;

import com.chan.study.common.message.IMessage;
import com.chan.study.common.message.local.consumer.ResultReferee;

/**
 * 业务操作
 *
 * @param <V> 消息类型
 * @param <W> 返回执行结果类型
 * @author CtChan
 */
@FunctionalInterface
public interface ICService<T, U, V extends IMessage<T, U>, W extends ResultReferee> {
    W execute(V iMessage);
}
