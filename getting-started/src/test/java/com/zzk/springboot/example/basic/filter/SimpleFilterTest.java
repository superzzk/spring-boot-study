package com.zzk.springboot.example.basic.filter;

import com.zzk.springboot.example.basic.controller.ExampleController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

@WebMvcTest(ExampleController.class)
class SimpleFilterTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testSimpleFilter() throws Exception {
        mockMvc.perform(get("/example"))
                .andExpect(header().string("some_header", "value"));
    }

}