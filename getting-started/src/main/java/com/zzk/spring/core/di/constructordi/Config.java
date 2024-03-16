package com.zzk.spring.core.di.constructordi;


import com.zzk.spring.core.di.constructordi.domain.Engine;
import com.zzk.spring.core.di.constructordi.domain.Transmission;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.zzk.springboot.example.di.constructordi")
public class Config {

    @Bean
    public Engine engine() {
        return new Engine("v8", 5);
    }

    @Bean
    public Transmission transmission() {
        return new Transmission("sliding");
    }
}
