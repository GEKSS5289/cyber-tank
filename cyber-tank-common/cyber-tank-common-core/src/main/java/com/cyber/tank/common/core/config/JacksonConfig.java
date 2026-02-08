package com.cyber.tank.common.core.config;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

/**
 * 统一 JSON 序列化配置
 */
@Configuration
public class JacksonConfig {

    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            // 1. 统一时区
            builder.timeZone(TimeZone.getTimeZone("GMT+8"));
            
            // 2. 日期格式化
            builder.simpleDateFormat(DATETIME_FORMAT);
            builder.serializers(new com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer(
                    DateTimeFormatter.ofPattern(DATETIME_FORMAT)));
            
            // 3. 关键：解决 Long 精度丢失问题
            // 将所有的 Long 类型在序列化时自动转为 String
            builder.serializerByType(Long.class, ToStringSerializer.instance);
            builder.serializerByType(Long.TYPE, ToStringSerializer.instance);
        };
    }
}
