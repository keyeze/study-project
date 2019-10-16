package com.chan.study.module.state.machine;

/**
 * 处理状态流转以及流转事件
 */
public interface IStatusMachine {

    //状态变更
    default void change(IStatus current, IStatus target,String key) throws StatusMachineException {
        IStatusManager manager = getStatusManager();
        if (!manager.isNext(current, target)) {
            throw new StatusMachineException("状态处理失败,状态无法流转到目标状态["+current+"->"+target+"]");
        }
        manager.exec(current,target,key);

    }

    IStatusManager getStatusManager();

}
