package com.zzk.springboot.annotation.qualifer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Failed {
    @Autowired
    FooService fooService;


    public static void main(String[] args) {
        SpringApplication.run(Failed.class, args);
    }
}
