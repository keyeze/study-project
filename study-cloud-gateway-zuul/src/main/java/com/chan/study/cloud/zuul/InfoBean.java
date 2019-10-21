package com.chan.study.cloud.zuul;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class InfoBean {
    @JSONField(name = "@type")
    private String classType;
    private String test;
}
