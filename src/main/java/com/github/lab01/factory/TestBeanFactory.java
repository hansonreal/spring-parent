package com.github.lab01.factory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author: hsr
 * @desc: TestBeanFactory
 * @since: 2024-11-12 16:39
 */
@Slf4j
public class TestBeanFactory {
    public static void main(String[] args) {
        // 初始化BeanFactory的一个实例
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 添加 bean 的定义（class 、scope、初始化、销毁等属性）
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder
                .genericBeanDefinition(BeanConfig.class)
                .setScope("singleton")
                .getBeanDefinition();
        // 注册 bean 的定义
        beanFactory.registerBeanDefinition("beanConfig", beanDefinition);

        for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
            // 为什么无法获取到bean1,bean2？ 无法解析@Configuration注解 ！ 此处需要添加BeanFactory的后置处理器
            log.info("beanDefinitionName:{}", beanDefinitionName);
        }


        // 给 beanFactory 添加一些Spring 内置的 后处理器
        AnnotationConfigUtils.registerAnnotationConfigProcessors(beanFactory);

        // BeanFactory后置处理器，补充了一些Bean的定义
        beanFactory.getBeansOfType(BeanFactoryPostProcessor.class)
                .values()
                .forEach(beanFactoryPostProcessor -> beanFactoryPostProcessor.postProcessBeanFactory(beanFactory));

        for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
            log.info("beanDefinitionName:{}", beanDefinitionName); // 此处已经可以获取到 bean1,bean2
        }


        //Bean1 bean1 = beanFactory.getBean(Bean1.class);
        //log.info("bean1:{}", bean1);
        // 此处 bean2 为 null，原因是因为@Autowired注解无法解析，需要添加BeanPostProcessor
        //log.info("bean2:{}", bean1.getBean2());

        // Bean 后置处理器，针对Bean的生命周期各个阶段提供扩展，例如@Autowired注解的解析,@Resource注解的解析
        beanFactory.getBeansOfType(BeanPostProcessor.class)
                .values()
                .forEach(beanFactory::addBeanPostProcessor);

        // 已经可以拿到bean2
        Bean1 bean1 = beanFactory.getBean(Bean1.class);
        log.info("bean2:{}", bean1.getBean2());


    }

    @Configuration
    static class BeanConfig {

        @Bean
        public Bean1 bean1() {
            return new Bean1();
        }

        @Bean
        public Bean2 bean2() {
            return new Bean2();
        }

    }

    static class Bean1 {

        public Bean1() {
            log.info("Bean1 构造函数");
        }

        @Autowired
        private Bean2 bean2;

        public Bean2 getBean2() {
            return bean2;
        }

    }

    static class Bean2 {
        public Bean2() {
            log.info("Bean2 构造函数");
        }
    }
}
