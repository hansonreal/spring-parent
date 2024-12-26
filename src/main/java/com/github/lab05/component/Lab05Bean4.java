package com.github.lab05.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

/**
 * @author: hsr
 * @desc:
 * @since: 2024-11-28 17:18
 */
@Slf4j
@Controller
public class Lab05Bean4 {
    public Lab05Bean4() {
        log.info("Lab05Bean4 被Spring 容器管理 ");
    }
}
