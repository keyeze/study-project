package com.chan.study.module.state.machine;

public interface IStatusManager {

    boolean isNext(IStatus current, IStatus target);

    void exec(IStatus current, IStatus target, String key);
}
