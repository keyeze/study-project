package com.chan.study.cloud.web.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

@ControllerAdvice
public class JsonpBodyAdvice extends AbstractJsonpResponseBodyAdvice {
    protected JsonpBodyAdvice() {
        super("callback");
    }
}
