package com.chan.study.spring.profile;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile(value = "prod")
@Component
public class ProdEnvironment extends Environment {
    protected String name = "Product Environment";

}
