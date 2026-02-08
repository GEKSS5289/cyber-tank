package com.cyber.tank;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 会员引擎业务模块启动类
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(basePackages = "com.cyber.tank.api")
public class AuthEngineStartApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthEngineStartApplication.class, args);
        System.out.println("====================================================");
        System.out.println("   Cyber-Tank Auth Engine 已成功启动   ");
        System.out.println("====================================================");
    }
}
