package com.github.lab04;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 * @author: HSR
 * @desc: BEAN 1
 * @since: 2024-11-25 17:49
 */
@Slf4j
public class Bean1 {
    private Bean2 bean2;

    @Autowired
    public void setBean2(Bean2 bean2) {
        log.info("Bean1 setBean2 @Autowired 生效 {}", bean2);
        this.bean2 = bean2;
    }

    private Bean3 bean3;

    @Resource
    public void setBean3(Bean3 bean3) {
        log.info("Bean1 setBean3 @Resource 生效 {}",bean3);
        this.bean3 = bean3;
    }


    private String javaHome;


    @Autowired
    public void setJavaHome(@Value("${JAVA_HOME}") String javaHome) {
        log.info("Bean1 setJavaHome @Value 生效 ,JAVA_HOME:{}", javaHome);
        this.javaHome = javaHome;
    }

    @PostConstruct
    public void init() {
        log.info("Bean1 init @PostConstruct生效 实例化方法执行之前 。 。 。");
    }

    @PreDestroy
    public void destroy(){
        log.info("Bean1 destroy  @PreDestroy生效 销毁方法执行之前 。 。 。");
    }

    @Override
    public String toString() {
        return "Bean1{" +
                "bean2=" + bean2 +
                ", bean3=" + bean3 +
                ", javaHome='" + javaHome + '\'' +
                '}';
    }
}
