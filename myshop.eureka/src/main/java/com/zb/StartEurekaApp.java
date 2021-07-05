package com.zb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/*管理用户开发微服务信息*/
@SpringBootApplication
@EnableEurekaServer
public class StartEurekaApp {
    public static void main(String[] args) {
        SpringApplication.run(StartEurekaApp.class);
    }
}
