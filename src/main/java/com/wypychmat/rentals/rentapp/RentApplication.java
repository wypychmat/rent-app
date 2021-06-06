package com.wypychmat.rentals.rentapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class RentApplication {

    public static void main(String[] args) {
        SpringApplication.run(RentApplication.class, args);
    }
}
