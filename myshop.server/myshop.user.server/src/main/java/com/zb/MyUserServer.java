package com.zb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class MyUserServer {
    public static void main(String[] args) {

        ConfigurableApplicationContext run = SpringApplication.run(MyUserServer.class);
//        KafkaTemplate bean = run.getBean(KafkaTemplate.class);
//        bean.send("dmservice", "调用了用户微服务" );
    }
}
