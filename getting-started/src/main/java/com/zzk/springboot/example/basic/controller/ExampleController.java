package com.zzk.springboot.example.basic.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/example")
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
}