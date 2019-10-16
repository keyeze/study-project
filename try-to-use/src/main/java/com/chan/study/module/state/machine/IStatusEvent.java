package com.chan.study.module.state.machine;

public interface IStatusEvent {
    void change(IStatus before,IStatus after);
}
