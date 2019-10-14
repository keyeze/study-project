package com.chan.study.common.message.serv.producer;

import com.chan.study.common.message.IMessage;
import com.chan.study.common.message.producer.IPService;

public interface IPreMessage<
        T,
        U,
        TV extends IMessage<T, U>> extends IMessage<T, U>, IPService<T, U, TV> {
}
