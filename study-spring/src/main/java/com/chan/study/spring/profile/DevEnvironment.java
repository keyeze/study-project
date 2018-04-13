package com.chan.study.spring.profile;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//@Profile(value = "Dev")
@Component
public class DevEnvironment {
    @Value("${environment.name}")
    private String name;
//    = "Development Environment";

    public void say() {
        System.out.println("This is " + name);
    }

}
