package com.arui.srb.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author ...
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan({"com.arui.srb", "com.arui.common"})
public class ServiceOssApplicatoin {
    public static void main(String[] args) {
        SpringApplication.run(ServiceOssApplicatoin.class, args);
    }
}
