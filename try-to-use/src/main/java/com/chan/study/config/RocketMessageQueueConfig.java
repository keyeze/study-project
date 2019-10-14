package com.chan.study.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author keyez
 * @date 2018/12/7
 */
@Slf4j
@Configuration
public class RocketMessageQueueConfig {
    private String groupName;
    private String namesrvAddr;
    private String instanceName;
    private int maxMessageSize;
    private int sendMsgTimeout;

    @Bean
    public DefaultMQProducer getRocketMQProducer() {
        if (StringUtils.isBlank(this.groupName)) {
            throw new RuntimeException("groupName is blank");
        }
        if (StringUtils.isBlank(this.namesrvAddr)) {
            throw new RuntimeException("nameServerAddr is blank");
        }
        if (StringUtils.isBlank(this.instanceName)) {
            throw new RuntimeException("instanceName is blank");
        }
        DefaultMQProducer producer;
        producer = new DefaultMQProducer(this.groupName);
        producer.setNamesrvAddr(this.namesrvAddr);
        producer.setInstanceName(instanceName);
        producer.setMaxMessageSize(this.maxMessageSize);
        producer.setSendMsgTimeout(this.sendMsgTimeout);
        try {
            producer.start();
            log.info(String.format("producer is start ! groupName:[%s],namesrvAddr:[%s]"
                    , this.groupName, this.namesrvAddr));
        } catch (MQClientException e) {
            log.error(String.format("producer is error {}"
                    , e.getMessage(), e));
            throw new RuntimeException();
        }
        return producer;
    }

}
