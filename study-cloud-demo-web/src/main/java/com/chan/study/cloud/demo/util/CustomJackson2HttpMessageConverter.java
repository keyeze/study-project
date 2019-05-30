package com.chan.study.cloud.demo.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.lang.reflect.Type;

public class CustomJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

    private static final ObjectMapper MAPPER = Jackson2ObjectMapperBuilder.json().build();



    @Override
    public boolean canRead(Class clazz, MediaType mediaType) {
        return false;
    }

    @Override
    protected boolean canRead(MediaType mediaType) {
        return false;
    }

    @Override
    protected Object readInternal(Class clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        throw new RuntimeException("不提供读服务");
    }

    @Override
    public Object read(Type type, Class contextClass, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        throw new RuntimeException("不提供读服务");
    }

    @Override
    protected void writeInternal(Object o, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        String in = MAPPER.writeValueAsString(o);
        outputMessage.getHeaders().setContentLength(in.length());
        StreamUtils.copy(in, this.getDefaultCharset(), outputMessage.getBody());
    }


}
