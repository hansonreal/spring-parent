package com.github.lab04;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.core.MethodParameter;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author: hsr
 * @desc: AutowiredAnnotationBeanPostProcessor 运行分析
 */
@Slf4j
public class DigInAutowired {
    public static void main(String[] args) throws Throwable {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerSingleton("bean2", new Lab04Bean2());
        beanFactory.registerSingleton("bean3", new Lab04Bean3());
        beanFactory.setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());
        beanFactory.addEmbeddedValueResolver(new StandardEnvironment()::resolvePlaceholders);// 解析 ${} 占位符

        // 1.查找哪些属性、方法加了 @Autowired注解 ，称之为 InjectionMetadata
        AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
        autowiredAnnotationBeanPostProcessor.setBeanFactory(beanFactory);
        Lab04Bean1 lab04Bean1 = new Lab04Bean1();
        log.info("lab04Bean1 执行依赖注入之前:{}", lab04Bean1);


        Method findAutowiringMetadata = ReflectionUtils.findMethod(
                AutowiredAnnotationBeanPostProcessor.class,
                "findAutowiringMetadata",
                String.class, Class.class, PropertyValues.class);

        assert findAutowiringMetadata != null;
        ReflectionUtils.makeAccessible(findAutowiringMetadata);


        InjectionMetadata injectionMetadata =
                (InjectionMetadata) ReflectionUtils.invokeMethod(
                        findAutowiringMetadata,
                        autowiredAnnotationBeanPostProcessor,
                        "lab04Bean1", Lab04Bean1.class, null);// 获取Bean1中加了@Autowired 的 成员变量 方法参数信息
        log.info("injectionMetadata:{}", injectionMetadata);

        // 2.调用InjectionMetadata来进行依赖注入，注入时按类型查找值

        assert injectionMetadata != null;
        injectionMetadata.inject(lab04Bean1, "lab04Bean1", null);

        log.info("lab04Bean1 执行依赖注入之后:{}", lab04Bean1);

        // 3.如何按类型查找
        Field filedBean3 = ReflectionUtils.findField(Lab04Bean1.class, "bean3");

        assert filedBean3 != null;
        DependencyDescriptor dd1
                = new DependencyDescriptor(filedBean3, false);
        Object o1 = beanFactory.doResolveDependency(dd1, null, null, null);
        log.info("doResolveDependency:{}", o1); // 解析成员变量bean3


        Method setBean2Method = ReflectionUtils.findMethod(Lab04Bean1.class, "setBean2", Lab04Bean2.class);
        DependencyDescriptor dd2 = new DependencyDescriptor(new MethodParameter(Objects.requireNonNull(setBean2Method), 0), false);
        Object o2 = beanFactory.doResolveDependency(dd2, null, null, null);
        log.info("doResolveDependency:{}", o2); // 解析方法参数bean2
    }
}
