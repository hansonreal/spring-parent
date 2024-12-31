package com.github.lab05.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;

import java.io.IOException;

/**
 * @author: hsr
 * @desc: 模拟mybatis mapper 扫描器
 * @since: 2024-12-31 10:52
 */
@Slf4j
public class MapperPostProcessor implements BeanDefinitionRegistryPostProcessor {


    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

        try {
            PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resourcePatternResolver.getResources("com/github/lab05/mapper/*.class");
            CachingMetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory();
            AnnotationBeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();
            for (Resource resource : resources) {
                MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
                ClassMetadata classMetadata = metadataReader.getClassMetadata();
                boolean anInterface = classMetadata.isInterface();
                if (anInterface) {
                    String className = classMetadata.getClassName();
                    log.info("扫描到mapper接口:{}", className);
                    // 模拟 com.github.lab05.Lab05Config#mapper1() 方法
                    BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder
                            .genericBeanDefinition(MapperFactoryBean.class) // 使用MapperFactoryBean来创建Mapper的代理对象
                            .addConstructorArgValue(className) // 设置Mapper接口的类名
                            .setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE) // 自动注入
                            ;
                    // 构建 beanDefinition
                    AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
                    // 设置bean的名称
                    AbstractBeanDefinition bd2 = BeanDefinitionBuilder.genericBeanDefinition(className).getBeanDefinition();
                    String generateBeanName = beanNameGenerator.generateBeanName(bd2, registry);
                    // 注册bean
                    registry.registerBeanDefinition(generateBeanName, beanDefinition);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}
