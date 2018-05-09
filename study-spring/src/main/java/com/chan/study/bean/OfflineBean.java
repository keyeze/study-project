package com.chan.study.bean;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

//@Component
public class OfflineBean {
    @Resource
    public DemoBean bean;

    @PostConstruct
    public void init() {
        System.out.println(this + " OfflineBean is inited !!!");
    }
}
