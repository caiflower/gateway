package com.caiflower.cloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * gateway统一网关启动类
 *
 * @author caiflower
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class BootApplication {

    public static void main(String[] args) {
        System.out.println("项目开始启动");
        SpringApplication.run(BootApplication.class, args);
        System.out.println("项目启动完成");
    }

}
