package com.chan.study.bean;


public class ResponseResult<T> {

    private String code;
    private String message;
    private T result;

    public String getCode() {
        return code;
    }

    public ResponseResult<T> setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ResponseResult<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getResult() {
        return result;
    }

    public ResponseResult<T> setResult(T result) {
        this.result = result;
        return this;
    }
}
