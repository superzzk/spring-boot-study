package com.zzk.spring.core.di.store;

import com.zzk.spring.core.di.store.AppConfig;
import com.zzk.spring.core.di.store.Store;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
public class AppConfigUnitTest {

    @Autowired
    @Qualifier("storeThroughConstructorInjection")
    private Store storeByConstructorInjection;
    
    @Autowired
    @Qualifier("storeThroughSetterInjection")
    private Store storeBySetterInjection;

    @Test
    public void givenValidXmlConfig_WhenInjectStoreByConstructorInjection_ThenBeanIsNotNull() {
        assertNotNull(storeByConstructorInjection);
        assertNotNull(storeByConstructorInjection.getItem());

        assertTrue(storeByConstructorInjection.getItem() == storeBySetterInjection.getItem());
    }

    @Test
    public void givenValidXmlConfig_WhenInjectStoreBySetterInjection_ThenBeanIsNotNull() {
        assertNotNull(storeBySetterInjection);
        assertNotNull(storeByConstructorInjection.getItem());
    }
}
