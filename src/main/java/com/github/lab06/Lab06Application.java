package com.github.lab06;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;

/**
 * @author: hsr
 * @desc: AWARE接口、InitializingBean接口
 * @since: 2024-12-31 11:35
 */
@Slf4j
public class Lab06Application {
    public static void main(String[] args) {

        /**
         * 1、Aware接口用于注入一些与容器相关的信息，例如：
         * a、BeanNameAware：注入bean的名称
         * b、BeanFactoryAware：注入bean的工厂
         * c、ApplicationContextAware：注入bean的上下文
         * d、EmbeddedValueResolverAware：注入字符串解析器
         */

        GenericApplicationContext context = new GenericApplicationContext();

        // 验证 BeanNameAware 、ApplicationContextAware 、InitializingBean
        // context.registerBean("lab06Bean1", Lab06Bean1.class);

        // context.registerBean("lab06Config01",Lab06Config01.class);

        context.registerBean("lab06Config02", Lab06Config02.class);

        /**
         * 存在疑问：b、c、d的功能可以通过@Autowired实现，为什么还需要Aware接口？
         * 简单的说，
         * 1、@Autowired 的解析需要用到bean后处理器，属于扩展功能
         * 2、Aware接口属于内置功能，不添加任何扩展，Spring就能识别，某些情况下扩展功能会失效，而内置功能不会失效
         *  比如：@Autowired注入的bean是单例的，而Aware接口注入的bean是原型，每次获取都是新的
         * 3、Aware接口可以获取到一些与容器相关的信息，而这些信息可能无法通过其他方式获取。
         */
        // 添加 AutowiredAnnotationBeanPostProcessor 处理器，用于解析@Autowired注解
        context.registerBean(AutowiredAnnotationBeanPostProcessor.class);

        // 添加 CommonAnnotationBeanPostProcessor 处理器，用于解析@PostConstruct、@PreDestroy等注解
        context.registerBean(CommonAnnotationBeanPostProcessor.class);

        // 添加 ConfigurationClassPostProcessor 处理器，用于解析@Configuration注解
        context.registerBean(ConfigurationClassPostProcessor.class);

        /**
         * 刷新容器
         * 1、beanFactory 后处理器
         * 2、添加Bean 后处理器
         * 3、初始化单例
         */
        context.refresh();


        // 关闭容器
        context.close();

        /**
         * 学到了什么？
         * 1、Aware接口 提供了一种【内置】的注入手段，可以注入BeanFactory、ApplicationContext等容器相关的信息
         * 2、InitializingBean 接口 提供了一种【内置】的初始化手段，可以在bean初始化时执行一些操作
         * 3、内置的注入和初始化不受扩展功能的影响，总会被执行，因此Spring框架内部的类常使用它们
         */


    }
}
