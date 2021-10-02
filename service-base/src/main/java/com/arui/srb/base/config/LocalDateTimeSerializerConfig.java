package com.arui.srb.base.config;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 输出Json时间格式化问题，这是全局格式化，
 * 如果需要特殊处理，在类的属性上添加注解 @JsonFormat(pattern = "yyyy-MM-dd")
 * @author ...
 */
@Configuration
public class LocalDateTimeSerializerConfig {
    @Value("${spring.jackson.date-format:yyyy-MM-dd HH:mm:ss}")
    private String pattern;
    public LocalDateTimeSerializer localDateTimeDeserializer() {
        return new
                LocalDateTimeSerializer(DateTimeFormatter.ofPattern(pattern));
    }
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer
    jackson2ObjectMapperBuilderCustomizer() {
        return builder -> builder.serializerByType(LocalDateTime.class,
                localDateTimeDeserializer());
    }
}