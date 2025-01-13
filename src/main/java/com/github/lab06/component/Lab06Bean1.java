package com.github.lab06.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;

@Slf4j
public class Lab06Bean1 implements BeanNameAware, ApplicationContextAware, InitializingBean {
    @Override
    public void setBeanName(String name) {
        log.info("当前Bean:{}的名称是:{}", this, name);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.info("当前Bean:{}所在的容器是:{}", this, applicationContext);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("当前Bean:{}执行的初始化操作", this);
    }


    @Autowired
    public void abc(ApplicationContext applicationContext) {
        log.info("当前Bean:{}使用@Autowired注入ApplicationContext", this);
    }

    @PostConstruct
    public void init() {
        log.info("当前Bean:{}使用@PostConstruct来执行初始化方法", this);
    }
}
