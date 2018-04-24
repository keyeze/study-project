package com.chan.study.spring.ioc;


import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

public class BeanFactoryDemo {
    /**
     * 使用 DefaultListableBeanFactory 来完成IoC容器生成
     *
     * 启动到获得Bean对象的过程:
     *  1)  创建 IoC 配置文件的抽象资源,这个抽象资源包含了 BeanDefinition 的定义信息;
     *  2)  创建一个 BeanFactory ,这里使用 DefaultListableBeanFactory;
     *  3)  创建一个载入 BeanDefinition 的读取器,这里使用 XmlBeanDefinitionReader 来载入XML文件形式的BeanDefinition,
     *      通过一个回调配置给 Bean Factory
     *  4)  从定义好的资源位置读入配置信息,具体的解析过程由XmlBeanDefinitionReader完成。
     *
     */
    @Test
    public void usingDefaultListableBeanFactoryInit() {
        ClassPathResource resource = new ClassPathResource("spring.xml");
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(resource);
        System.out.println(factory.getBean("demoBean"));
    }

    @Test
    public void usingFileSystemXmlApplicationContextInit(){
        ClassPathResource resource = new ClassPathResource("classpath:spring.xml");
        FileSystemXmlApplicationContext applicationContext = new FileSystemXmlApplicationContext(resource.getPath());
        System.out.println(applicationContext.getBean("demoBean"));

    }
}
