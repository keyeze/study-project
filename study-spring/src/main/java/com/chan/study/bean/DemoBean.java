package com.chan.study.bean;


public class DemoBean {
    private String id;
    private String name;


    public String getId() {
        return id;
    }

    public DemoBean setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public DemoBean setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "DemoBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
