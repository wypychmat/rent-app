package com.wypychmat.rentals.rentapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.MessageSource;

@SpringBootApplication
public class RentApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(RentApplication.class, args);
    }

}
