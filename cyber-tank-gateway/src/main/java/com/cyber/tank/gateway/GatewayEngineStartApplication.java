package com.cyber.tank.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 网关启动类
 * 注意：网关基于 WebFlux，千万不要引入 spring-boot-starter-web 依赖
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GatewayEngineStartApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayEngineStartApplication.class, args);
        System.out.println("====================================================");
        System.out.println("   Cyber-Tank Gateway Engine 已成功启动   ");
        System.out.println("====================================================");
    }
}
