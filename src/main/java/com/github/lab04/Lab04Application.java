package com.github.lab04;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.context.support.GenericApplicationContext;

/**
 * @author: HSR
 * @desc: Bean后处理器的作用
 */
@Slf4j
public class Lab04Application {
    public static void main(String[] args) {

        // 普通的 应用上下文对象，没有多余的功能是一个【干净】的容器
        GenericApplicationContext context = new GenericApplicationContext();

        // 向容器重注册三个Bean对象
        context.registerBean("bean1",Bean1.class);
        context.registerBean("bean2",Bean2.class);
        context.registerBean("bean3",Bean3.class);
        context.registerBean("bean4",Bean4.class);


        /**
         * AutowireCandidateResolver
         * 用于确定特定的Bean定义是否符合特定的依赖项的候选者的策略接口
         */
        context.getDefaultListableBeanFactory()
                // 默认情况下无法解析@Value注解，这里替换解析器
                .setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());
        // 往容器中添加 用于解析@Autowired,@Qualifier,@Value注解 的 后置处理Bean
        context.registerBean(AutowiredAnnotationBeanPostProcessor.class);

        // 往容器中添加 用于解析@Resource、@PostConstruct、@PreDestroy注解 的 后置处理Bean
        context.registerBean(CommonAnnotationBeanPostProcessor.class);

        // 往容器中添加 用于解析ConfigurationProperties注解 的 后置处理Bean
        ConfigurationPropertiesBindingPostProcessor.register(context);

        // 初始化容器，执行BeanFactory后置处理器，Bean后置处理器，初始化所有单例对象
        context.refresh();


        log.info(" Bean4:{}", context.getBean(Bean4.class));


        // 关闭容器
        context.close();

    }
}
