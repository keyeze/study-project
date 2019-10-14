package com.chan.study.common.message.local.consumer;

@FunctionalInterface
public interface ResultReferee {
    boolean judge();

    default <T> void judgeExe(IService<T> service) {
        if (judge()) {
            service.execute();
        }
    }

    interface IService<T> {
        void execute();
    }
}
