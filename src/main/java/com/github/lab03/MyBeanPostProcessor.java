package com.github.lab03;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author: hsr
 * @desc: 自定义Bean后置处理器
 * @since: 2024-11-22 15:48
 */
@Slf4j
@Component
public class MyBeanPostProcessor implements InstantiationAwareBeanPostProcessor, DestructionAwareBeanPostProcessor {


    @Override
    public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {
        if (bean.getClass() == LifeCycleBean.class) {
            log.info("【LifeCycleBean before destroy . . .】");
        }
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if (beanClass == LifeCycleBean.class) {
            log.info("【LifeCycleBean before constructor . . .】");
        }
        return InstantiationAwareBeanPostProcessor.super.postProcessBeforeInstantiation(beanClass, beanName);
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if (bean.getClass() == LifeCycleBean.class) {
            log.info("【LifeCycleBean after constructor . . .】");
        }
        return InstantiationAwareBeanPostProcessor.super.postProcessAfterInstantiation(bean, beanName);
    }

    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        if (bean.getClass() == LifeCycleBean.class) {
            log.info("【LifeCycleBean attribute assignment(@Autowried、@Value、@Resource) . . .】");
        }
        return InstantiationAwareBeanPostProcessor.super.postProcessProperties(pvs, bean, beanName);
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass() == LifeCycleBean.class) {
            log.info("【LifeCycleBean before init(@PostConstruct、@ConfigurationProperties) . . .】");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass() == LifeCycleBean.class) {
            log.info("【LifeCycleBean after init . . .】");
        }
        return bean;
    }

}
