package com.zzk.springboot.example.basic.properties;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {PropertiesWithJavaConfig.class}, loader = AnnotationConfigContextLoader.class)
class PropertiesWithJavaConfigTest {

    @Autowired
    private Environment env;

    @Value("${key.something}")
    private String injectedProperty;

    @Value("${nonexist:default}")
    private String defaultValue;

    /**
     * spel使用system properties
     */
    @Value("#{systemProperties['priority']}")
    private String spelValue;

    @Value("#{myBean.name}")
    private String myBeanName;

    @Test
    final void givenContextIsInitialized_thenNoException() {
        assertEquals("val", injectedProperty);
        assertEquals("default", defaultValue);
        assertEquals("val", env.getProperty("key.something"));
        assertNull(spelValue);
        assertEquals("myBean name", myBeanName);
    }

}