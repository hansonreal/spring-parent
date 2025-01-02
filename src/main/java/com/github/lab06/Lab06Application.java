package com.github.lab06;

import lombok.extern.slf4j.Slf4j;

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
    }
}
