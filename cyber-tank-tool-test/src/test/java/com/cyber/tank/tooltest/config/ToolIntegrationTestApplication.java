package com.cyber.tank.tooltest.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.cyber.tank")
@MapperScan("com.cyber.tank.tooltest.mapper")
public class ToolIntegrationTestApplication {
}
