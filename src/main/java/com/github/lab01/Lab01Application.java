package com.github.lab01;

import com.github.lab01.listenter.event.MyEvent;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.Resource;

import java.util.Locale;

/**
 * @author: hsr
 * @desc: 实验
 */
@Slf4j
@SpringBootApplication
public class Lab01Application {

    @SneakyThrows
    public static void main(String[] args) {
        // 返回 可配置的 应用上下文 使用 CTRL+ALT+U 可以快速查看类图
        ConfigurableApplicationContext context
                = SpringApplication.run(Lab01Application.class, args);

        // BeanFactory 是ApplicationContext的父接口 ，Spring 框架的核心接口，各个ApplicationContext都实现了BeanFactory接口

        log.info("{}", context);

        // 由MessageSource 提供的国际化功能
        String msgKeyHiZh = context.getMessage("msg_key_hi", null, Locale.CHINA);
        log.info("msgKeyHiZh:{}", msgKeyHiZh);
        String msgKeyHiEn = context.getMessage("msg_key_hi", null, Locale.ENGLISH);
        log.info("msgKeyHiEn:{}", msgKeyHiEn);

        // 获取资源 ResourceLoader
        Resource[] resources = context.getResources("classpath*:META-INF/spring.factories");
        for (Resource resource : resources) {
            log.info("{}", resource);
        }

        // 获取环境变量 Environment
        ConfigurableEnvironment environment = context.getEnvironment();
        String javaHome = environment.getProperty("JAVA_HOME");
        log.info("JAVA_HOME:{}", javaHome);

        // 发布事件 ApplicationEventPublisher
        context.publishEvent(new MyEvent("Hello World!"));



    }
}
