package com.zzk.springboot.example.di.lookup;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.zzk.springboot.example.di.methodinjections")
public class AppConfig {
}
