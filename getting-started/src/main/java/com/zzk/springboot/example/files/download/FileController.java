package com.zzk.springboot.example.files.download;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;

/**
 *
 * @date 2023/5/13 2:34 PM
 */
@Controller
@RequestMapping("download/fileController")
public class FileController {
    @RequestMapping(path = "/download", method = RequestMethod.GET)
    public ResponseEntity<Resource> download() throws IOException {

        File file = ResourceUtils.getFile("classpath:logback.xml");
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @RequestMapping(path = "/download2", method = RequestMethod.GET)
    public ResponseEntity<Resource> download2() throws IOException {

        File file = ResourceUtils.getFile("classpath:logback.xml");
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(file.toPath()));

        HttpHeaders headers = new HttpHeaders(); headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=logback.xml");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @GetMapping("download3")
    public StreamingResponseBody largeFile(HttpServletResponse response) throws FileNotFoundException {

        File file = ResourceUtils.getFile("classpath:logback.xml");
        response.setContentType(MediaType.APPLICATION_XML.toString());
        response.setHeader(
                HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=logback.xml");

        return outputStream -> {
            int bytesRead;
            byte[] buffer = new byte[1024];
            final FileInputStream is = new FileInputStream(file);
            while ((bytesRead = is.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        };
    }
}
