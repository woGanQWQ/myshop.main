package com.zb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
@EnableEurekaClient
public class MyGateWayServer {
    public static void main(String[] args) {

        ConfigurableApplicationContext run = SpringApplication.run(MyGateWayServer.class);
        KafkaTemplate bean = run.getBean(KafkaTemplate.class);
        bean.send("dmservice", "dmservice","调用了用户微服务" );
    }
}
