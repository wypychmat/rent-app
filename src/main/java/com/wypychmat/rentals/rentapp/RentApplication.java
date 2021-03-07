package com.wypychmat.rentals.rentapp;

import com.wypychmat.rentals.rentapp.app.core.security.JwtConfig;
import com.wypychmat.rentals.rentapp.app.core.security.RsaKeyConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({RsaKeyConfig.class, JwtConfig.class})
public class RentApplication {

    public static void main(String[] args) {
        SpringApplication.run(RentApplication.class, args);
    }

}
