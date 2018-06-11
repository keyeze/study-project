package com.chan.study.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * 启动 web 的纯注解配置,基于5.0.5版本,
 * todo: 注意,请尽量不要使用 XML 和 properties 配置,使用 yml , java 以及 标签 直接配置
 *
 * @author keyez
 */

@Configuration
@EnableWebMvc
@ComponentScan(
        basePackages = {"com.chan.study.web"},
        useDefaultFilters = false,
        includeFilters = {
                @ComponentScan.Filter(value = Controller.class)
        }
)
public class WebApplicationConfig implements WebMvcConfigurer {
    @Resource
    Validator validator;

    @Bean
    public MessageSource validMessageSource() {
        return new ReloadableResourceBundleMessageSource();
    }

    @Bean
    public LocalValidatorFactoryBean validator(MessageSource validMessageSource) {
        this.validator = new LocalValidatorFactoryBean() {{
            this.setValidationMessageSource(validMessageSource);
            this.setProviderClass(HibernateValidator.class);
        }};
        return (LocalValidatorFactoryBean) validator;
    }

    @Override
    public Validator getValidator() {
        return validator;
    }

}
