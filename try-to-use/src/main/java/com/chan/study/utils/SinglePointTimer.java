package com.chan.study.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SinglePointTimer {
    private final static long DEFAULT_STEP = 50;
    private Map<String, ListenerTask> taskMap = new ConcurrentHashMap<>();
    private Runnable singlePointTimer = () -> {
        while (true) {
            List<String> removeList = new ArrayList<>();
            long currentTime = System.currentTimeMillis();
            taskMap.forEach((key, value) -> {
                if (value.isListener(currentTime)) {
                    value.execute(currentTime);
                }
                if (value.isEnd(currentTime)) {
                    value.endExecute(currentTime);
                    removeList.add(key);
                }
            });
            removeList.forEach(item -> taskMap.remove(item));
            try {
                Thread.sleep(DEFAULT_STEP);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    public void pushTask(Condition endCondition, Listener endListener, Condition listenerCondition, Listener listener) {
        taskMap.put(UUID.randomUUID().toString(), new ListenerTask() {
            @Override
            public void endExecute(long currentTime) {
                endListener.doIfPresence(currentTime);
            }

            @Override
            public void execute(long currentTime) {
                listener.doIfPresence(currentTime);
            }

            @Override
            public boolean isEnd(long currentTime) {
                return endCondition.isConform(currentTime);
            }

            @Override
            public boolean isListener(long currentTime) {
                return listenerCondition.isConform(currentTime);
            }
        });
    }

    public void pushTask(Condition endCondition, Condition listenerCondition, Listener listener) {
        pushTask(endCondition, currentTime -> {
        }, listenerCondition, listener);
    }

    @FunctionalInterface
    public interface Listener {
        void doIfPresence(long currentTime);
    }

    @FunctionalInterface
    public interface Condition {
        boolean isConform(long currentTime);
    }

    public interface ListenerTask {
        void endExecute(long currentTime);

        void execute(long currentTime);

        boolean isEnd(long currentTime);

        boolean isListener(long currentTime);
    }

    public Runnable getSinglePointTimer() {
        return singlePointTimer;
    }
}
