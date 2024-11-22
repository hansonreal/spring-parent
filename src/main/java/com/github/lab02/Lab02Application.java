package com.github.lab02;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @author: hsr
 * @desc: 常见ApplicationContext的实现
 */
@Slf4j
public class Lab02Application {

    public static void main(String[] args) {
        // ①
        // caseForClassPathXmlApplicationContext();
        // ②
        // caseForFileSystemXmlApplicationContext();
        // ③
        // caseForAnnotationConfigApplicationContext();
        // ④
        caseForAnnotationConfigServletWebServerApplicationContext();


/*        // ① 与 ② 的底层相当于如下代码
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();
        log.info("读取之前 .... ");
        for (String beanDefinitionName : defaultListableBeanFactory.getBeanDefinitionNames()) {
            log.info("Bean 名称 : {}", beanDefinitionName);
        }
        log.info("读取之后 .... ");
        // XmlBeanDefinitionReader 将读取到的 BeanDefinition 注册到 DefaultListableBeanFactory 中
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(defaultListableBeanFactory);
        // 从类路径下载入配置文件
        // xmlBeanDefinitionReader.loadBeanDefinitions(new ClassPathResource("b02.xml")); // 相当于 ①
        xmlBeanDefinitionReader.loadBeanDefinitions(new FileSystemResource("src\\main\\resources\\b02.xml")); // 相当于 ②
        for (String beanDefinitionName : defaultListableBeanFactory.getBeanDefinitionNames()) {
            log.info("Bean 名称 : {}", beanDefinitionName);
        }*/

    }


    /**
     * 基于 classpath 下 xml文件格式来构建
     */
    private static void caseForClassPathXmlApplicationContext() {
        log.info("ClassPathXmlApplicationContext - - - - - - 开始");

        ClassPathXmlApplicationContext classPathXmlApplicationContext
                = new ClassPathXmlApplicationContext("b01.xml");
        Bean2 bean2 = classPathXmlApplicationContext.getBean(Bean2.class);
        log.info("bean1: {}", bean2.getBean1());

        log.info("ClassPathXmlApplicationContext - - - - - - 结束");
    }

    /**
     * 基于 磁盘 下 xml文件格式来构建
     */
    private static void caseForFileSystemXmlApplicationContext() {
        log.info("FileSystemXmlApplicationContext - - - - - - 开始");
        FileSystemXmlApplicationContext fileSystemXmlApplicationContext
                = new FileSystemXmlApplicationContext("src\\main\\resources\\b02.xml");
        Bean2 bean2 = fileSystemXmlApplicationContext.getBean(Bean2.class);
        log.info("bean1: {}", bean2.getBean1());

        log.info("FileSystemXmlApplicationContext - - - - - - 结束");
    }

    /**
     * 基于Java配置类来创建
     */
    private static void caseForAnnotationConfigApplicationContext() {
        log.info("AnnotationConfigApplicationContext - - - - - - 开始");

        AnnotationConfigApplicationContext annotationConfigApplicationContext
                = new AnnotationConfigApplicationContext(Config.class);

        String[] beanDefinitionNames = annotationConfigApplicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            log.info("Bean 名称 : {}", beanDefinitionName);
        }
        log.info("AnnotationConfigApplicationContext - - - - - - 结束");
    }

    /**
     * 基于Java配置类来创建,用于web环境
     */
    private static void caseForAnnotationConfigServletWebServerApplicationContext() {
        log.info("AnnotationConfigWebApplicationContext - - - - - - 开始");
        AnnotationConfigServletWebServerApplicationContext webServerApplicationContext
                = new AnnotationConfigServletWebServerApplicationContext(WebConfig.class);

        // 浏览器 输入 http://localhost:8080/hello 进行测试


        log.info("AnnotationConfigWebApplicationContext - - - - - - 结束");
    }

    @Configuration
    static class WebConfig {

        /**
         * 构建内置web容器
         *
         * @return
         */
        @Bean
        public ServletWebServerFactory webServerFactory() {
            return new TomcatServletWebServerFactory();
        }

        /**
         * 构建前端控制器
         *
         * @return
         */
        @Bean
        public DispatcherServlet dispatcherServlet() {
            return new DispatcherServlet();
        }


        /**
         * 将前端控制器与Web容器建立关系
         *
         * @return
         */
        @Bean
        public DispatcherServletRegistrationBean dispatcherServletRegistrationBean() {
            return new DispatcherServletRegistrationBean(dispatcherServlet(), "/");
        }

        @Bean("/hello")
        public Controller controller1() {
            return (request, response) -> {
                response.getWriter().write("Hello World");
                return null;
            };
        }


    }


    @Configuration
    static class Config {
        @Bean
        public Bean1 bean1() {
            return new Bean1();
        }

        @Bean
        public Bean2 bean2(Bean1 bean1) {
            Bean2 bean2 = new Bean2();
            bean2.setBean1(bean1);
            return bean2;
        }
    }

    static class Bean1 {

    }

    static class Bean2 {
        private Bean1 bean1;

        public Bean1 getBean1() {
            return bean1;
        }

        public void setBean1(Bean1 bean1) {
            this.bean1 = bean1;
        }
    }


}
