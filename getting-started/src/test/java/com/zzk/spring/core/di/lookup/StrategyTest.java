package com.zzk.spring.core.di.lookup;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class StrategyTest {
    private ConfigurableApplicationContext context;

    @Test
    void demo() {
        context = new AnnotationConfigApplicationContext(AppConfig.class);

        String rt = context.getBean("processor", Processor.class).process("A");
        assertEquals("Strategy A", rt);
        rt = context.getBean("processor", Processor.class).process("B");
        assertEquals("Strategy B", rt);

    }
}
