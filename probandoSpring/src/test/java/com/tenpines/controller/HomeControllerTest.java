package com.tenpines.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by pino on 08/09/16.
 */
public class HomeControllerTest {

    @Test
    public void contextLoads() {
    }

    @Test
    public void firstTest() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response;
        response = restTemplate.getForEntity("http://localhost:8080/", String.class);
        System.out.println("Received!:");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();

    }


}