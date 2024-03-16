package com.zzk.camel.route.configuration;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;


@CamelSpringBootTest
@SpringBootTest(classes = MyCamelApplication.class)
class MyCamelApplicationTest {
    @Autowired
    private CamelContext camelContext;

    @Test
    void shouldProduceMessages() throws Exception {
        // we expect that one or more messages is automatic done by the Camel
        // route as it uses a timer to trigger
        NotifyBuilder notify = new NotifyBuilder(camelContext).whenDone(10).create();

        assertTrue(notify.matches(15, TimeUnit.SECONDS));
    }

}