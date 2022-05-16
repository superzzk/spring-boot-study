package com.zzk.springboot.example.basic.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/example")
public class ExampleController {

	@RequestMapping("")
	String home() {
		return "Hello World!";
	}

}