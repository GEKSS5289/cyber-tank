package com.cyber.tank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 会员引擎业务模块启动类
 */
@EnableDiscoveryClient
@SpringBootApplication
/**
 * MemberEngineStartApplication 的核心定义。
 */
public class MemberEngineStartApplication {
    /**
     * main 方法。
     * @param args 参数。
     */
    public static void main(String[] args) {
        SpringApplication.run(MemberEngineStartApplication.class, args);
        System.out.println("====================================================");
        System.out.println("   Cyber-Tank Member Engine 已成功启动   ");
        System.out.println("====================================================");
    }
}