package com.github.lab03;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author: hsr
 * @desc: Bean 生命周期
 * @since: 2024-11-22 15:45
 */
@Slf4j
@SpringBootApplication
public class Lab03Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext context
                = SpringApplication.run(Lab03Application.class, args);
        context.close();
    }
}
