package com.github.lab03;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author: hsr
 * @desc: 生命周期Bean
 * @since: 2024-11-22 15:46
 */
@Slf4j
@Component
public class LifeCycleBean {
    public LifeCycleBean() {
        log.info("【LifeCycleBean Constructor . . .】");
    }

    @Autowired
    public void autowired(@Value("${JAVA_HOME}") String name) {
        log.info("【LifeCycleBean autowired . . . name:{}】", name);
    }

    @PostConstruct
    public void init() {
        log.info("【LifeCycleBean init . . .】");
    }

    @PreDestroy
    public void destroy() {
        log.info("【LifeCycleBean destroy . . .】");
    }
}
