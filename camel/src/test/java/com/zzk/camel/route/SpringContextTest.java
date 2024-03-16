package com.zzk.camel.route;

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
class SpringContextTest extends CamelSpringTestSupport {

    @Override
    protected AbstractApplicationContext createApplicationContext() {
        return new AnnotationConfigApplicationContext(SpringContextTest.class);
    }

    @Test
    void testPlacingOrders() throws Exception {
        fluentTemplate.to("direct:begin").send();
        final MockEndpoint mockEndpoint = getMockEndpoint("mock:done");
        mockEndpoint.expectedMessageCount(1);
        mockEndpoint.assertIsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:begin")
                        .bean("myBean", "create")
                        .process(exchange -> {
                            final MyBean myBean = exchange.getMessage().getBody(MyBean.class);
                            log.info("process:{}", myBean);
                        })
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

        public MyBean create() {
            return new MyBean(2, "Alice");
        }
    }
}
