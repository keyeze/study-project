package com.chan.study.cloud.demo;


import java.util.EventObject;

public class DemoSource extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     *
     * @throws IllegalArgumentException if source is null.
     */
    public DemoSource(Object source) {
        super(source);
    }
}
