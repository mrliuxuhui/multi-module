package com.flyingwillow.resilience4jdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.flyingwillow.resilience4jdemo.client")
public class Resilience4jDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(Resilience4jDemoApplication.class, args);
    }

}
