package com.zzk.springboot.example.basic.properties;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class Properties2Pojo {

    @Component
    @PropertySource("springboot/properties/pojo.properties")
    @ConfigurationProperties(prefix = "pojo")
    @Validated
    @Data
    public static class Pojo {
        @NotBlank
        private String name;
        @Length(max = 4, min = 1)
        private  String description;

        private String[] arrayValues;
        private List<String> tags;
        private Map<String, Boolean> additionalHeaders;
    }

    public static void main(String[] args) {
        SpringApplication.run(Properties2Pojo.class, args);
    }

    @Component
    public class Runner implements ApplicationRunner {
        @Autowired
        private Pojo pojo;
        @Override
        public void run(ApplicationArguments var1) throws Exception {
            System.out.println(pojo);
        }
    }
}
