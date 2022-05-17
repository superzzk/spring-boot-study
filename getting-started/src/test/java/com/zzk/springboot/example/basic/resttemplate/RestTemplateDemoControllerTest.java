package com.zzk.springboot.example.basic.resttemplate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class RestTemplateDemoControllerTest {

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void rest_call_hello(){
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> entity =new HttpEntity<>(headers);
        final String response = restTemplate.getForObject("http://localhost:8000/rest-template/rest-call-hello", String.class);
        Assertions.assertEquals("hello", response);
    }
}