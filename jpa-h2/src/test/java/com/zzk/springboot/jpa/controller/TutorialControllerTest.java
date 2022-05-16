package com.zzk.springboot.jpa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzk.springboot.jpa.entity.Tutorial;
import com.zzk.springboot.jpa.repository.TutorialRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TutorialController.class)
public class TutorialControllerTest {

    @MockBean
    private TutorialRepository repository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createTutorial() throws Exception {
        Tutorial tutorial = new Tutorial("title", "description", false);
        mockMvc.perform(post("/api/tutorials").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tutorial)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void getById() throws Exception {
        long id = 1L;
        Tutorial tutorial = new Tutorial(id, "title", "description", false);
        when(repository.findById(id)).thenReturn(Optional.of(tutorial));
        mockMvc.perform(get("/api/tutorials/{id}", id)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.description").value(tutorial.getDescription()))
                .andExpect(jsonPath("$.published").value(tutorial.isPublished()))
                .andDo(print());
    }

    @Test
    void getById_not_found() throws Exception {
        long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/tutorials/{id}", id))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void getAll() throws Exception{
        List<Tutorial> tutorials = new ArrayList<>(
                Arrays.asList(new Tutorial(1, "Spring Boot @WebMvcTest 1", "Description 1", true),
                        new Tutorial(2, "Spring Boot @WebMvcTest 2", "Description 2", true),
                        new Tutorial(3, "Spring Boot @WebMvcTest 3", "Description 3", true)));
        when(repository.findAll()).thenReturn(tutorials);
        mockMvc.perform(get("/api/tutorials")).andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(tutorials.size()))
                .andDo(print());
    }

    @Test
    void getAll_pageable() throws Exception {
        List<Tutorial> tutorials = new ArrayList<>(
                Arrays.asList(new Tutorial(1, "Spring Boot @WebMvcTest 1", "Description 1", true),
                        new Tutorial(2, "Spring Boot @WebMvcTest 2", "Description 2", true),
                        new Tutorial(3, "Spring Boot @WebMvcTest 3", "Description 3", true)));
        Page<Tutorial> tutorialPage = new PageImpl<>(tutorials, PageRequest.of(0,10), 10);
        when(repository.findAll(any(Pageable.class))).thenReturn(tutorialPage);
        mockMvc.perform(get("/api/tutorials/page"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tutorials.length()").value(3))
                .andDo(print());
    }

    @Test
    void getByTitle() throws Exception {
        List<Tutorial> tutorials = new ArrayList<>(
                Arrays.asList(new Tutorial(1, "Spring Boot @WebMvcTest", "Description 1", true),
                        new Tutorial(3, "Spring Boot Web MVC", "Description 3", true)));
        String title = "Boot";

        when(repository.findByTitleContaining(title)).thenReturn(tutorials);
        
        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
        paramsMap.add("title", title);
        mockMvc.perform(get("/api/tutorials").params(paramsMap))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(tutorials.size()))
                .andDo(print());
        
        tutorials = Collections.emptyList();
        when(repository.findByTitleContaining(title)).thenReturn(tutorials);
        mockMvc.perform(get("/api/tutorials").params(paramsMap))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    void shouldReturnNoContentWhenFilter() throws Exception {
        String title = "BezKoder";
        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
        paramsMap.add("title", title);

        List<Tutorial> tutorials = Collections.emptyList();
        when(repository.findByTitleContaining(title)).thenReturn(tutorials);
        mockMvc.perform(get("/api/tutorials").params(paramsMap))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    void shouldUpdateTutorial() throws Exception {
        long id = 1L;
        Tutorial tutorial = new Tutorial(id, "Spring Boot @WebMvcTest", "Description", false);
        Tutorial updatedTutorial = new Tutorial(id, "Updated", "Updated", true);
        when(repository.findById(id)).thenReturn(Optional.of(tutorial));
        when(repository.save(any(Tutorial.class))).thenReturn(updatedTutorial);

        mockMvc.perform(put("/api/tutorials/{id}", id).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedTutorial)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(updatedTutorial.getTitle()))
                .andExpect(jsonPath("$.description").value(updatedTutorial.getDescription()))
                .andExpect(jsonPath("$.published").value(updatedTutorial.isPublished()))
                .andDo(print());
    }

    @Test
    void shouldReturnNotFoundUpdateTutorial() throws Exception {
        long id = 1L;
        Tutorial updatedTutorial = new Tutorial(id, "Updated", "Updated", true);
        when(repository.findById(id)).thenReturn(Optional.empty());
        when(repository.save(any(Tutorial.class))).thenReturn(updatedTutorial);
        mockMvc.perform(put("/api/tutorials/{id}", id).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedTutorial)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void shouldDeleteTutorial() throws Exception {
        long id = 1L;
        doNothing().when(repository).deleteById(id);
        mockMvc.perform(delete("/api/tutorials/{id}", id))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    void shouldDeleteAllTutorials() throws Exception {
        doNothing().when(repository).deleteAll();
        mockMvc.perform(delete("/api/tutorials"))
                .andExpect(status().isNoContent())
                .andDo(print());
    }
}
