package com.chechup;

import org.apache.dubbo.config.spring.context.annotation.EnableDubboConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author ldq
 * @version 1.0
 * @date 2024-6-24 22:22
 * @Description:
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDubboConfig
public class MobileApplication {
    public static void main(String[] args) {
        SpringApplication.run(MobileApplication.class);
    }
}
