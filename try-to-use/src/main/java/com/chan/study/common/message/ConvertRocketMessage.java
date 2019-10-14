package com.chan.study.common.message;

/**
 * 可转为RocketMessage可用消息体
 *
 * @param <T>
 */
public interface ConvertRocketMessage<T> extends IMessage {
    byte[] toBtyes();

    T toMsg();
}
