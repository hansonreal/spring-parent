package com.github.lab05.postprocessor;

import com.github.lab05.Lab05Config;
import lombok.extern.slf4j.Slf4j;
import org.junit.platform.commons.util.AnnotationUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.MethodMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

/**
 * @author: hsr
 * @desc: 模拟接卸@Bean注解的处理器
 * @since: 2024-12-19 15:26
 */
@Slf4j
public class AtBeanPostProcessor implements BeanFactoryPostProcessor {
    /**
     * Modify the application context's internal bean factory after its standard
     * initialization. All bean definitions will have been loaded, but no beans
     * will have been instantiated yet. This allows for overriding or adding
     * properties even to eager-initializing beans.
     *
     * @param beanFactory the bean factory used by the application context
     * @throws BeansException in case of errors
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        try {
            Optional<Configuration> configurationOptional =
                    AnnotationUtils.findAnnotation(Lab05Config.class, Configuration.class);
            if (configurationOptional.isPresent()) {
                CachingMetadataReaderFactory cachingMetadataReaderFactory = new CachingMetadataReaderFactory();
                ClassPathResource classPathResource = new ClassPathResource("com/github/lab05/Lab05Config.class");
                MetadataReader metadataReader = null;

                metadataReader = cachingMetadataReaderFactory.getMetadataReader(classPathResource);

                AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata(); // 获取所有标注注解相关的元素
                Set<MethodMetadata> annotatedMethods = annotationMetadata.getAnnotatedMethods(Bean.class.getName()); // 获取所有标注@Bean注解的方法
                for (MethodMetadata methodMetadata : annotatedMethods) {
                    String methodName = methodMetadata.getMethodName();

                    log.info("标注了@Bean注解的方法名称:{}", methodName);
                    BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition();
                    // 创建config工厂下面的工厂方法BeanDefinition
                    beanDefinitionBuilder.setFactoryMethodOnBean(methodName, "lab05Config");
                    // 指定自动装配模式
                    beanDefinitionBuilder.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_CONSTRUCTOR);

                    // 获取initMethod
                    String initMethod = methodMetadata.getAnnotationAttributes(Bean.class.getName()).get("initMethod").toString();
                    if (StringUtils.hasText(initMethod)) {
                        log.info("标注了@Bean注解的方法名称:{}，initMethod:{}", methodName, initMethod);
                        beanDefinitionBuilder.setInitMethodName(initMethod);
                    }

                    // 注册BeanDefinition
                    AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();

                    if (beanFactory instanceof DefaultListableBeanFactory) {
                        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) beanFactory;
                        defaultListableBeanFactory.registerBeanDefinition(methodName, beanDefinition);
                    }


                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
