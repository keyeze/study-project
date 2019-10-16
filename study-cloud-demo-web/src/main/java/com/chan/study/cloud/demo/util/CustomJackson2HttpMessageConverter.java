package com.chan.study.cloud.demo.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.StreamUtils;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class CustomJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

    private static final ObjectMapper MAPPER = Jackson2ObjectMapperBuilder.json().build();

    @Resource(name = "mappingJackson2HttpMessageConverter")
    private MappingJackson2HttpMessageConverter converter;

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
        try (ByteArrayOutputStream tempStream = new ByteArrayOutputStream()) {
            HttpOutputMessage tempOut = new HttpOutputMessage() {
                @Override
                public OutputStream getBody() throws IOException {
                    return tempStream;
                }

                @Override
                public HttpHeaders getHeaders() {
                    return outputMessage.getHeaders();
                }
            };
            super.writeInternal(o, type, tempOut);
            String in = tempStream.toString(Optional.ofNullable(getDefaultCharset()).map(Charset::name).orElse(Charset.defaultCharset().name()));
            outputMessage.getHeaders().setContentLength(in.getBytes(StandardCharsets.UTF_8).length);
            StreamUtils.copy(in, this.getDefaultCharset(), outputMessage.getBody());
        }
    }


}
