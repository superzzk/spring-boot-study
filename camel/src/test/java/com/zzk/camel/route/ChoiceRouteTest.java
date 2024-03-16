package com.zzk.camel.route;

import com.zzk.camel.demo.BeanDemo;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;


@Configuration
class ChoiceRouteTest extends CamelSpringTestSupport {

    @Override
    protected AbstractApplicationContext createApplicationContext() {
        return new AnnotationConfigApplicationContext(ChoiceRouteTest.class);
    }

    @Test
    void testPlacingOrders() throws Exception {
        fluentTemplate.to("direct:begin").withBodyAs(myBean(), MyBean.class).send();
        final MockEndpoint mockAlice = getMockEndpoint("mock:john");
        mockAlice.expectedMessageCount(1);
        mockAlice.assertIsSatisfied();

        fluentTemplate.to("direct:begin").withBodyAs(new MyBean(1, "error"), MyBean.class).send();
        final MockEndpoint mockError = getMockEndpoint("mock:error");
        mockError.expectedMessageCount(1);
        mockError.assertIsSatisfied();

    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:begin")
                        .process(exchange -> {
                            final MyBean myBean = exchange.getMessage().getBody(MyBean.class);
                            log.info("process:{}", myBean);
                            exchange.getMessage().setHeader("name", myBean.getName());
                            exchange.getMessage().setHeader("id", myBean.getId());
                        })
                        .choice()
                            .when(header("name").isEqualTo("John"))
                                .log("Name is John")
                                .to("mock:john").stop()
                            .when(header("name").isEqualTo("Alice"))
                                .log("Name is Alice")
                                .to("mock:alice").stop()
                            .otherwise()
                                .log("Name is unknown")
                                .to("mock:error").stop()
                        .end()
                        .to("mock:done");
            }
        };
    }

    @Bean
    MyBean myBean() {
        return new MyBean(1, "John");
    }

    @Data
    @AllArgsConstructor
    public class MyBean {
        private Integer id;
        private String name;
    }
}
