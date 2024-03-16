package com.zzk.springboot.example.basic.aop.annotation;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        final ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
        final Service service = ctx.getBean(Service.class);
        service.serve();
    }

    @Component
    public class Service {
        @SneakyThrows
        @LogExecutionTime
        public void serve()  {
            Thread.sleep(2000);
        }
    }
}