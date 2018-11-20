package com.shizijie.beta.spring;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.support.EncodedResource;

public class SpringDemo  {
    public static void main(String[] args) {
        //解析xml并注册bean
        ApplicationContext bf=new ClassPathXmlApplicationContext("/test.xml");
    }
}
