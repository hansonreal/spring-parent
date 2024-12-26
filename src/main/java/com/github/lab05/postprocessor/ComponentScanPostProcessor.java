package com.github.lab05.postprocessor;

import com.github.lab05.Lab05Config;
import lombok.extern.slf4j.Slf4j;
import org.junit.platform.commons.util.AnnotationUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author: hsr
 * @desc: ComponentScan 注解 处理类
 * @since: 2024-11-29 16:50
 */
@Slf4j
public class ComponentScanPostProcessor implements BeanFactoryPostProcessor {
    /**
     * Modify the application context's internal bean factory after its standard
     * initialization. All bean definitions will have been loaded, but no beans
     * will have been instantiated yet. This allows for overriding or adding
     * properties even to eager-initializing beans.
     *
     * @param configurableListableBeanFactory the bean factory used by the application context
     * @throws BeansException in case of errors
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        try {
            Optional<ComponentScan> componentScanOptional =
                    AnnotationUtils.findAnnotation(Lab05Config.class, ComponentScan.class);
            ComponentScan componentScan =
                    componentScanOptional.orElseThrow(() -> new RuntimeException("未找到 @ComponentScan 注解"));
            String[] basePackages = componentScan.basePackages();
            CachingMetadataReaderFactory cachingMetadataReaderFactory = new CachingMetadataReaderFactory();
            BeanNameGenerator beanNameGenerator = AnnotationBeanNameGenerator.INSTANCE;
            PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
            for (String basePackage : basePackages) {
                // 基础包名 转换为 路径格式
                String path = basePackage.replace(".", "/");
                path = "classpath*:" + path + "/**/*.class";
                Resource[] resources = pathMatchingResourcePatternResolver.getResources(path);
                for (Resource resource : resources) {
                    MetadataReader metadataReader = cachingMetadataReaderFactory.getMetadataReader(resource);
                    ClassMetadata classMetadata = metadataReader.getClassMetadata();
                    String className = classMetadata.getClassName();
                    // 判断类上是否有 @Component 注解
                    AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
                    boolean hasAnnotation = annotationMetadata.hasAnnotation(Component.class.getName());
                    boolean hasMetaAnnotation = annotationMetadata.hasMetaAnnotation(Component.class.getName());
                    if (hasAnnotation || hasMetaAnnotation) {
                        // 创建beanDefinition
                        BeanDefinitionBuilder builder =
                                BeanDefinitionBuilder.genericBeanDefinition(className);
                        AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();
                        if (configurableListableBeanFactory instanceof DefaultListableBeanFactory) {
                            DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) configurableListableBeanFactory;
                            // bean别名生成器
                            String beanName = beanNameGenerator.generateBeanName(beanDefinition, beanFactory);
                            // 注册bean
                            beanFactory.registerBeanDefinition(beanName, beanDefinition);
                        }
                    }
                }

            }
        } catch (Exception ignored) {

        }
    }
}
