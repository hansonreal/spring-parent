package com.github.lab04;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;

/**
 * @author: hsr
 * @desc: AutowiredAnnotationBeanPostProcessor 运行分析
 */
@Slf4j
public class DigInAutowired {
    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerSingleton("bean2",Bean2.class);
        beanFactory.registerSingleton("bean3",Bean3.class);
        beanFactory.setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());

        // 1.查找哪些属性、方法加了 @Autowired注解 ，称之为 InjectionMetadata

        // 2.调用InjectionMetadata来进行依赖注入，注入时按类型查找值

        // 3.如何按类型查找
    }
}
