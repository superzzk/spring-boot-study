package com.zzk.springboot.example.basic.resttemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/rest-template")
public class RestTemplateDemoController {

    private static final Logger log = LoggerFactory.getLogger(RestTemplateDemoController.class);

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/rest-call-hello")
    public String restCallHello() {
        log.info("enter restCallHello method");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        final String body = restTemplate.exchange("http://localhost:8000/rest-template/hello",
                                                    HttpMethod.GET, entity, String.class)
                .getBody();
        log.info(body);
        return body;
    }
}
