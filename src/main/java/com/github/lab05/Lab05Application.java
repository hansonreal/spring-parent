package com.github.lab05;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;

/**
 * @author: hsr
 * @desc: BeanFactory 后置处理器
 * @since: 2024-11-28 17:16
 */
@Slf4j
public class Lab05Application {
    public static void main(String[] args) {
        // 普通的 应用上下文对象，没有多余的功能是一个【干净】的容器
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean("config", Lab05Config.class);

        // 添加 ConfigurationClassPostProcessor 后置处理器 用于解析 @ComponentScan、@Bean、@Import、@ImportResource 注解
        context.registerBean(ConfigurationClassPostProcessor.class);

        // 添加 MapperScannerConfigurer 后置处理器 用于扫描指定包路径下的 Mapper 接口并注册到容器中 同理也会解析@MapperScanner注解
        context.registerBean(MapperScannerConfigurer.class, bd -> {
            bd.getPropertyValues().add("basePackage", "com.github.lab05.mapper");
        });


        // 初始化容器
        context.refresh();


        // 输出容器中全部Bean的名称
        for (String beanDefinitionName : context.getBeanDefinitionNames()) {
            log.info("beanDefinitionName: {}", beanDefinitionName);
        }


        // 销毁容器
        context.close();
    }
}
