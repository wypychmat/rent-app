package com.wypychmat.rentals.rentapp.app.core;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;


@SpringBootTest
@Testcontainers
public abstract class TestContainerBaseWithEmail {

    @Container
    public static MySQLContainer DB_CONTAINER = new MySQLContainer("mysql:8.0.23");

    static {
        DB_CONTAINER.start();
        System.setProperty("MYSQL_TEST_PORT", String.valueOf(DB_CONTAINER.getFirstMappedPort()));
    }

    @Container
    static GenericContainer greenMailContainer = new GenericContainer<>(DockerImageName.parse("greenmail/standalone:1.6.2"))
            .withEnv("GREENMAIL_OPTS", "-Dgreenmail.setup.test.all -Dgreenmail.hostname=0.0.0.0 -Dgreenmail.users=test:test")
            .withExposedPorts(3025);

    @DynamicPropertySource
    static void configureMailHost(DynamicPropertyRegistry registry) {
        registry.add("spring.mail.host", greenMailContainer::getHost);
        registry.add("spring.mail.port", greenMailContainer::getFirstMappedPort);
    }
}

