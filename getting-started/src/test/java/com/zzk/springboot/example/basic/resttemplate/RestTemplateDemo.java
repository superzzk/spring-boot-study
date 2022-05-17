package com.zzk.springboot.example.basic.resttemplate;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.zzk.springboot.example.basic.resttemplate.FooController.Foo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RestTemplateDemo {
    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;
    private static final String fooResourceUrl = "http://localhost:" + 8000 + "/foos";

    @BeforeEach
    public void before() {
        restTemplate = new RestTemplate();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void getFoo() {
        final URI uri = UriComponentsBuilder.fromUriString(fooResourceUrl + "/{id}").build(ImmutableMap.of("id", 1L));
        final Foo foo = restTemplate.getForObject(uri, Foo.class);
        assertEquals("foo1",foo.getName());
    }

    @Test
    public void getAllFoo() throws JsonProcessingException {
        final URI uri = UriComponentsBuilder.fromUriString(fooResourceUrl).build().toUri();
        final String response = restTemplate.getForObject(uri, String.class);

        final List<Foo> foos = objectMapper.readValue(response, new TypeReference<List<Foo>>() {});
        assertEquals(1, foos.size());
    }

    @Test
    public void postFoo(){
        final URI uri = UriComponentsBuilder.fromUriString(fooResourceUrl).build().toUri();
        final Foo foo = restTemplate.postForObject(uri, new Foo(2L, "foo2"), Foo.class);
        assertEquals(2L, foo.getId());
    }

    @Test
    public void postForLocation() {
        HttpEntity<Foo> entity = new HttpEntity<>(new Foo(2L,"foo"));
        final URI uri = restTemplate.postForLocation(fooResourceUrl, entity);
        System.out.println(uri);
    }


}
