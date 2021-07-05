package com.zb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class OAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(OAuthApplication.class,args);
//        String pwd = new BCryptPasswordEncoder().
//        System.out.println(pwd);
        //$2a$10$KbbCWhbo61lTCz0BA6yuPOo3f7RL51r7vjgmKxwyN/67IBK60LISu
        //$2a$10$ON0t8BT13i5O4VJj0..6V.Hx6xC2axipgnY7TVLlKhXzxnRU5sUMG
    }


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}