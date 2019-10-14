package com.chan.study.cloud.authentication.domain;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class Payload {
    private String uuid;
    private long timestamp;
    private long random;

    public JSONObject toJSON() {
        return (JSONObject) JSON.toJSON(this);
    }

}
