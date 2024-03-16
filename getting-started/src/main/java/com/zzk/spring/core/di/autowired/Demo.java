package com.zzk.spring.core.di.autowired;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

@Configuration
@ComponentScan("com.zzk.springboot.example.di.autowired")
public class Demo {

    public static void main(String[] args) {
        final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Demo.class);
        final UserService userService = ctx.getBean(UserService.class);
        Assert.notNull(userService.accountService);
    }
}
