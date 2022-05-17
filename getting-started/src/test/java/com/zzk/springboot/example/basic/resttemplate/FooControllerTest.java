package com.zzk.springboot.example.basic.resttemplate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcResultMatchersDsl;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FooController.class)
class FooControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllFoos() throws Exception {
        mockMvc.perform(get("/foos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1L));
    }

    @Test
    void getFoo() throws Exception {
        mockMvc.perform(get("/foos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void update() {

    }

    @Test
    void postFoo() {
    }

    @Test
    void deleteFoo() {
    }
}