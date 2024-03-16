package com.zzk.springboot.example.basic.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/example")
@Slf4j
public class ExampleController {

	@RequestMapping("")
	String home() {
		return "Hello World!";
	}

	@RequestMapping("/response-header")
	String responseHeader(HttpServletResponse response) {
		response.addHeader("response_header", "value");
		return "with header";
	}

	@PostMapping
	public ResponseEntity<String> requestAsString(@RequestBody String request, @RequestHeader HttpHeaders headers) {
		log.info("json request as string:{}",request);
		return ResponseEntity.ok(request);
	}

	@PostMapping(value = "/greeting", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	@ResponseBody
	public String greetingJson(HttpEntity<String> httpEntity) {
		String json = httpEntity.getBody();
		log.info("json request as string:{}",json);
		return json;
	}
}