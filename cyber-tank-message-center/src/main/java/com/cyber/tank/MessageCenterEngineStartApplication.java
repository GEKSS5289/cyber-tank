package com.cyber.tank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 消息枢纽启动类。
 */
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.cyber.tank")
/**
 * MessageCenterEngineStartApplication 的核心定义。
 */
public class MessageCenterEngineStartApplication {
    /**
     * main 方法。
     * @param args 参数。
     */
    public static void main(String[] args) {
        SpringApplication.run(MessageCenterEngineStartApplication.class, args);
        System.out.println("====================================================");
        System.out.println("   Cyber-Tank Message Center 已成功启动   ");
        System.out.println("====================================================");
    }
}
