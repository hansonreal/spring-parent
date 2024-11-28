package com.github.lab05;

import lombok.extern.slf4j.Slf4j;
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
        context.registerBean("config", Config.class);

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
