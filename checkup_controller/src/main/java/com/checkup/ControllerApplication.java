package com.checkup;

import org.apache.dubbo.config.spring.context.annotation.EnableDubboConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author ldq
 * @version 1.0
 * @date 2024-6-23 13:09
 * @Description:
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDubboConfig
public class ControllerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ControllerApplication.class);
    }
}
