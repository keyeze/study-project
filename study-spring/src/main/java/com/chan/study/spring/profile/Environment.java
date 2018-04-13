package com.chan.study.spring.profile;

public abstract class Environment {
    protected String name = "Environment";

    public void say() {
        System.out.println("This is " + name);
    }

}
