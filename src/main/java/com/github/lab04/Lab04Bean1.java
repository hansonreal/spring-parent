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
public class Lab04Bean1 {
    private Lab04Bean2 lab04Bean2;

    @Autowired
    public void setBean2(Lab04Bean2 lab04Bean2) {
        log.info("Lab04Bean1 setBean2 @Autowired 生效 {}", lab04Bean2);
        this.lab04Bean2 = lab04Bean2;
    }

    @Autowired
    private Lab04Bean3 lab04Bean3;

    @Resource
    public void setBean3(Lab04Bean3 lab04Bean3) {
        log.info("Lab04Bean1 setBean3 @Resource 生效 {}", lab04Bean3);
        this.lab04Bean3 = lab04Bean3;
    }


    private String javaHome;


    @Autowired
    public void setJavaHome(@Value("${JAVA_HOME}") String javaHome) {
        log.info("Lab04Bean1 setJavaHome @Value 生效 ,JAVA_HOME:{}", javaHome);
        this.javaHome = javaHome;
    }

    @PostConstruct
    public void init() {
        log.info("Lab04Bean1 init @PostConstruct生效 实例化方法执行之前 。 。 。");
    }

    @PreDestroy
    public void destroy(){
        log.info("Lab04Bean1 destroy  @PreDestroy生效 销毁方法执行之前 。 。 。");
    }

    @Override
    public String toString() {
        return "Lab04Bean1{" +
                "lab04Bean2=" + lab04Bean2 +
                ", lab04Bean3=" + lab04Bean3 +
                ", javaHome='" + javaHome + '\'' +
                '}';
    }
}
