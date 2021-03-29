package com.wypychmat.rentals.rentapp.app.core.controller;


import com.wypychmat.rentals.rentapp.app.core.TestContainerBaseWithEmail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RegisterControllerTest extends TestContainerBaseWithEmail {

    @Autowired
    private TestRestTemplate testRestTemplate;


    @Test
    void shouldCreateUser() throws Exception {

        String payload = "{\"email\": \"testemail@gmail.com\",\"username\": \"testUser\",\"lastName\" : \"testUser\",\"firstName\" : \"testUser\",\"password\" :\"12345678@qQ\",\"rePassword\": \"12345678@qQ\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(payload, headers);

        ResponseEntity<Object> response = this.testRestTemplate.postForEntity("/api/register/v1", request, Object.class);

        assertEquals(201, response.getStatusCodeValue());


    }

}