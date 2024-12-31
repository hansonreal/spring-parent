package com.github.lab05;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author: hsr
 * @desc: 配置类
 * @since: 2024-11-28 17:18
 */
@Slf4j
@Configuration
@ComponentScan(basePackages = "com.github.lab05.component")
public class Lab05Config {

    @Bean
    public Lab05Bean1 bean1() {
        return new Lab05Bean1();
    }

    @Bean
    public HikariDataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setUsername("root");
        hikariConfig.setPassword("root");
        hikariConfig.setJdbcUrl("jdbc:mysql://localhost:3306/test");
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource) {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);

        return sqlSessionFactoryBean;
    }


//    @Bean
//    public MapperFactoryBean<Mapper1> mapper1(SqlSessionFactory sqlSessionFactory) {
//        MapperFactoryBean<Mapper1> mapper1MapperFactoryBean = new MapperFactoryBean<>(Mapper1.class);
//        mapper1MapperFactoryBean.setSqlSessionFactory(sqlSessionFactory);
//        return mapper1MapperFactoryBean;
//    }
//
//    @Bean
//    public MapperFactoryBean<Mapper2> mapper2(SqlSessionFactory sqlSessionFactory) {
//        MapperFactoryBean<Mapper2> mapper2MapperFactoryBean = new MapperFactoryBean<>(Mapper2.class);
//        mapper2MapperFactoryBean.setSqlSessionFactory(sqlSessionFactory);
//        return mapper2MapperFactoryBean;
//    }
}
