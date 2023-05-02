package com.zzk.springboot.annotation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@ComponentScan("com.zzk.springboot.example.basic")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
