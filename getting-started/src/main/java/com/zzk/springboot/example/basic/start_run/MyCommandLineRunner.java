package com.zzk.springboot.example.basic.start_run;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
@Order(value = 1)
public class MyCommandLineRunner implements CommandLineRunner {
    @Override
    public void run(String... var1) throws Exception {
        System.out.println("This will be execute when the project was started!");
    }
}
