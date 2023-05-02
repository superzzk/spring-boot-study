package com.zzk.springboot.annotation.primary.success;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@SpringBootApplication
public class Success {
    @Autowired
    @Qualifier("johnEmployee")
    public Employee employee;

    @Bean
    public Employee johnEmployee() {
        return new Employee("John");
    }

    @Bean
    @Primary
    public Employee tonyEmployee() {
        return new Employee("Tony");
    }

    public static class Employee {
        public String name;

        public Employee(String name) {
            this.name = name;
        }
    }

    public static void main(String[] args) {
        final ConfigurableApplicationContext ctx = SpringApplication.run(Success.class, args);
        final Success bean = ctx.getBean(Success.class);
        // qualifier priority than primary
        System.out.println(bean.employee.name);
    }
}
