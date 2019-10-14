package com.chan.study.common.message.serv.producer;


import com.chan.study.common.message.IMessage;
import com.chan.study.common.message.producer.IPService;

@FunctionalInterface
public interface IServPService<
        T,
        U,
        V extends IPreMessage<T, U, TV>,
        TV extends IMessage<T, U>>
        extends IPService<T, U, V> {
}
