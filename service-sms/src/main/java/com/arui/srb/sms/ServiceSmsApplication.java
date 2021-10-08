package com.arui.srb.sms;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * 暂时不需要数据源，需要排除数据源的加载，否则报找不到数据源错误
 * @author ...
 */
//@EnableEncryptableProperties
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan({"com.arui.srb", "com.arui.common"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.arui.srb.sms.client"})
public class ServiceSmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceSmsApplication.class, args);
    }
}
