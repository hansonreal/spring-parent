package com.github.lab06;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author: hsr
 * @desc: Lab06Config01
 * @since: 2024-12-31 11:36
 */
@Slf4j
@Configuration
public class Lab06Config01 {

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.info("注入ApplicationContext");
    }

    @PostConstruct
    public void afterPropertiesSet() throws Exception {
        log.info("初始化");
    }


    @Bean
    public BeanFactoryPostProcessor processor1() {
        return beanFactory -> {
            log.info(" 执行 processor1");
        };
    }
}
