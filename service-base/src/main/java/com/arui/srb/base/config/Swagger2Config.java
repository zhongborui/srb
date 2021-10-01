package com.arui.srb.base.config;

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
    public Docket adminApiConfig(){
        return new Docket(DocumentationType.SWAGGER_2)
                // api文档分组名
                .groupName("adminApi")
                .apiInfo(adminApiInfo())
                .select()
                // 正则匹配请求路径admin开头的
                .paths(Predicates.and(PathSelectors.regex("/admin/.*")))
                .build();
    }

    /**
     * api文档元信息
     * @return
     */
    private ApiInfo adminApiInfo() {
        return new ApiInfoBuilder()
                .title("尚融宝后台管理系统-API文档")
                .description("本文档描述了尚融宝后台管理系统接口")
                .version("1.0")
                .contact(new Contact("arui", "https://arui.com", "arui@arui.com"))
                .build();
    }


    @Bean
    public Docket apiApiConfig(){
        return new Docket(DocumentationType.SWAGGER_2)
                // api文档分组名
                .groupName("api")
                .apiInfo(apiApiInfo())
                .select()
                // 正则匹配请求路径admin开头的
                .paths(Predicates.and(PathSelectors.regex("/api/.*")))
                .build();
    }

    /**
     * api文档元信息
     * @return
     */
    private ApiInfo apiApiInfo() {
        return new ApiInfoBuilder()
                .title("尚融宝-API文档")
                .description("本文档描述了尚融宝接口")
                .version("1.0")
                .contact(new Contact("arui", "https://arui.com", "arui@arui.com"))
                .build();
    }
}
