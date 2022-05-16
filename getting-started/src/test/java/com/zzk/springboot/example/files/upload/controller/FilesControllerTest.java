package com.zzk.springboot.example.files.upload.controller;

import com.zzk.springboot.example.files.upload.service.FilesStorageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.InputStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FilesController.class)
public class FilesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FilesStorageService storageService;

    @Test
    public void testUploadFile() throws Exception {

        final InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("files/testfile");
        MockMultipartFile file = new MockMultipartFile("file", "testfile", "file/text", is);
        mockMvc.perform(fileUpload( "/upload").file(file))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Uploaded the file successfully: testfile"))
                .andReturn();
    }
}