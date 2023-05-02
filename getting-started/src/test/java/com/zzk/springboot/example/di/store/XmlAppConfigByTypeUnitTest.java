package com.zzk.springboot.example.di.store;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Separate unit test class where only one Item object is available for 
 * autowiring. If the ioc-context.xml were used for autowiring by type, there 
 * would be multiple qualifying Item objects, causing a failure.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:/ioc-context-by-type.xml")
public class XmlAppConfigByTypeUnitTest {

    @Autowired
    @Qualifier("xml-store-by-autowire-type")
    private Store storeByAutowireInjectionByType;

    @Test
    public void givenValidXmlConfig_WhenInjectStoreByAutowireInjectionByType_ThenBeanIsNotNull() {
        assertNotNull(storeByAutowireInjectionByType);
        assertNotNull(storeByAutowireInjectionByType.getItem());
    }
}
