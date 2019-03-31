package com.chan.study;

import com.chan.study.config.DemoConfig;
import com.chan.study.config.MonitorComponentConfig;
import com.chan.study.config.WebConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationExcludeFilter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.context.annotation.*;

/**
 * {@link ComponentScan} {@link EntityScan} {@link SpringBootApplication}
 * 都会启动 默认package,获取所有的class文件
 * <p>
 * 通过 {@link Import} 来引入需要的配置,而不是通过上述方法获取全部类
 * <p>
 * 通过 {@link ImportResource} 来引入 XML 配置信息
 */

@SpringBootConfiguration
@EnableAutoConfiguration(exclude = {}, excludeName = {})
@ComponentScan(
        excludeFilters = {//排除过滤器
                @ComponentScan.Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class), //1.扫描beanFactory中继承了TypeExclueFilter的方法，遍历他们的match进行过滤
                @ComponentScan.Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class)//2.找到所有已经自动自动注入的方法过滤
        })

@Import({
        DemoConfig.class,
        WebConfig.class,    //web相关配置
        MonitorComponentConfig.class,   //服务器监控器相关配置
})
//@ImportResource({"spring-mvc.xml"})
public class SimpleApplication {
    @Value("${}")
    public static void main(String[] args) {
        SpringApplication.run(SimpleApplication.class, args);
    }

    @Override
    public int hashCode() {
        return 1;
    }
}
