package com.zzk.camel.demo;

import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Slf4j
public class DirectDemo {

    public static void main(String[] args) {
        SpringApplication.run(DirectDemo.class, args);
    }

    @Component
    class Run implements ApplicationRunner {
        @Resource
        FluentProducerTemplate fluentProducerTemplate;
        @Override
        public void run(ApplicationArguments args) throws Exception {
            final MyBean myBean = fluentProducerTemplate.withBodyAs(new MyBean(1, "John"), MyBean.class)
                    .to("direct:log")
                    .send().getMessage().getBody(MyBean.class);
            log.info("Processed bean: {}", myBean);

            final String json = fluentProducerTemplate.withBodyAs(new MyBean(1, "John"), MyBean.class)
                    .to("direct:json")
                    .send().getMessage().getBody(String.class);
            log.info("Processed json: {}", json);
        }
    }

    @Component
    class RestApi extends RouteBuilder {

        @Override
        public void configure() {
            from("direct:log").routeId("direct-log-route")
                    .tracing()
                    .log(">>> ${body.id}")
                    .log(">>> ${body.name}")
                    // .transform().simple("blue ${in.body.name}")
                    .process(new Processor() {
                        @Override
                        public void process(Exchange exchange) throws Exception {
                            MyBean bodyIn = (MyBean) exchange.getIn().getBody();
                            ExampleServices.example(bodyIn);
//                            exchange.getIn().setBody(bodyIn);
                        }
                    })
                    .to("direct:process");
            from("direct:process").routeId("direct-process-route")
                    .tracing()
                    .process(exchange -> {
                        log.info("process in:{}", exchange.getIn().getBody());
                    });

            from("direct:json").routeId("direct-json-route")
                    .tracing()
                    .marshal().json(JsonLibrary.Jackson);
        }
    }

    public class ExampleServices {
        public static void example(MyBean bodyIn) {
            bodyIn.setName( "Hello, " + bodyIn.getName() );
            bodyIn.setId(bodyIn.getId()*10);
        }
    }

    @Data
    @AllArgsConstructor
    public class MyBean {
        private Integer id;
        private String name;
    }
}
