package com.github.lab05.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author: hsr
 * @desc:
 * @since: 2024-11-28 17:18
 */
@Slf4j
@Component
public class Lab05Bean2 {
    public Lab05Bean2() {
        log.info("Lab05Bean2 被Spring 容器管理 ");
    }
}
