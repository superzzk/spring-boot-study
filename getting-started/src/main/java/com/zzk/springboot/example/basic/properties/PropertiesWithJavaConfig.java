package com.zzk.springboot.example.basic.properties;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("springboot/properties/foo.properties")
public class PropertiesWithJavaConfig {

    @Bean("myBean")
    public MyBean myBean() {
        MyBean myBean = new MyBean();
        myBean.setName("myBean name");
        return myBean;
    }

    @Data
    static class MyBean {
        private String name;
    }

}