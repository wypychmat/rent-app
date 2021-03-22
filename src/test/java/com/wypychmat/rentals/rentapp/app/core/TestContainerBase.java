package com.wypychmat.rentals.rentapp.app.core;

import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
public abstract class TestContainerBase {
    @Container
    public static MySQLContainer DB_CONTAINER = new MySQLContainer("mysql:8.0.23");

    static {
        DB_CONTAINER.start();
        System.setProperty("MYSQL_TEST_PORT", String.valueOf(DB_CONTAINER.getFirstMappedPort()));
    }
}
