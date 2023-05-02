package com.zzk.springboot.annotation.qualifer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Success {
    @Autowired
    @Qualifier("fooServiceImpl")
    FooService fooService;


    public static void main(String[] args) {
        SpringApplication.run(Success.class, args);
    }
}
