package com.zzk.camel.demo;

import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.FluentProducerTemplate;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Slf4j
public class BeanDemo {

    public static void main(String[] args) {
        SpringApplication.run(BeanDemo.class, args);
    }

    @Component
    class Run implements ApplicationRunner {
        @Resource
        FluentProducerTemplate fluentProducerTemplate;
        @Override
        public void run(ApplicationArguments args) throws Exception {
            final MyBean myBean = fluentProducerTemplate.to("direct:bean")
                    .send().getMessage().getBody(MyBean.class);
            log.info("result: {}", myBean);
        }
    }

    @Bean
    MyBean myBean(){
        return new MyBean(1, "John");
    }

    @Component
    class Route extends RouteBuilder {
        @Override
        public void configure() {
            from("direct:bean").routeId("direct-bean-route")
                    .bean("myBean", "create");
        }
    }

    @Data
    @AllArgsConstructor
    public class MyBean {
        private Integer id;
        private String name;

        public MyBean create(){
            return new MyBean(2, "Alice");
        }
    }
}
