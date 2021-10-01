package com.arui.srb.sms.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author ...
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Bean
    public Docket smsApiConfig(){
        return new Docket(DocumentationType.SWAGGER_2)
                // api文档分组名
                .groupName("api")
                .apiInfo(smsApiInfo())
                .select()
                // 正则匹配请求路径admin开头的
                .paths(Predicates.and(PathSelectors.regex("/api/.*")))
                .build();
    }

    /**
     * api文档元信息
     * @return
     */
    private ApiInfo smsApiInfo() {
        return new ApiInfoBuilder()
                .title("尚融宝短信服务系统-API文档")
                .description("本文档描述了尚融宝后台管理系统接口")
                .version("1.0")
                .contact(new Contact("arui", "https://arui.com", "arui@arui.com"))
                .build();
    }
}
