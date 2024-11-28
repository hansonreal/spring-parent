package com.github.lab04;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: HSR
 * @desc: BEAN 4
 * @since: 2024-11-25 17:49
 */
@Slf4j
@Data
@ConfigurationProperties(prefix = "java")
public class Lab04Bean4 {
    private String version;
    private String home;
}
