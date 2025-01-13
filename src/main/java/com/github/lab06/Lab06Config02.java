package com.github.lab06;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: hsr
 * @desc:
 * @since: 2024-12-31 11:36
 */
@Slf4j
@Configuration
public class Lab06Config02 implements ApplicationContextAware, InitializingBean {
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.info("注入ApplicationContext");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("初始化");
    }


    @Bean
    public BeanFactoryPostProcessor processor2() {
        return beanFactory -> {
            log.info(" 执行 processor2");
        };
    }

}
