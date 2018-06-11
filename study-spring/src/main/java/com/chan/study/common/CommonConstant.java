package com.chan.study.common;

public class CommonConstant {
    public enum CHECK_RESULT {
        SUCCESS("success"), FAIL("fail");
        public final String sign;

        CHECK_RESULT(String sign) {
            this.sign = sign;
        }

    }
}
