package com.github.lab07;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: hsr
 * @desc: Spring 提供了多种初始化和销毁方法、以及他们的执行顺序
 * @since: 2025-01-13 15:34
 */
@Slf4j
@SpringBootApplication
public class Lab07Application {
    public static void main(String[] args) {
        SpringApplication.run(Lab07Application.class, args);
    }
}
