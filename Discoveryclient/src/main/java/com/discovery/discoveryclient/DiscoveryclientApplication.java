package com.discovery.discoveryclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class DiscoveryclientApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiscoveryclientApplication.class, args);
    }

}
