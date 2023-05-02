package com.zzk.springboot.annotation.primary.failed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Failed {
    @Autowired
    Employee employee;

    @Bean
    public Employee johnEmployee() {
        return new Employee("John");
    }

    @Bean
    public Employee tonyEmployee() {
        return new Employee("Tony");
    }

    public static class Employee{
        private String name;
        public Employee(String name) {
            this.name = name;
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(Failed.class, args);
    }
}
