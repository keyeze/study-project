package com.chan.study.application;

import com.chan.study.config.DemoConfig;
import com.chan.study.config.MonitorComponentConfig;
import com.chan.study.config.WebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

/**
 * {@link ComponentScan} {@link EntityScan} {@link SpringBootApplication}
 * 都会启动 默认package,获取所有的class文件
 * <p>
 * 通过 {@link Import} 来引入需要的配置,而不是通过上述方法获取全部类
 * <p>
 * 通过 {@link ImportResource} 来引入 XML 配置信息
 */

@SpringBootApplication
@Import({
        DemoConfig.class,
        WebConfig.class,    //web相关配置
        MonitorComponentConfig.class,   //服务器监控器相关配置
})
//@ImportResource({"spring-mvc.xml"})
public class SimpleApplication {
    public static void main(String[] args) {
        SpringApplication.run(SimpleApplication.class, args);
    }

}
