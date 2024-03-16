package com.zzk.spring.core.naming.configuration;

import com.zzk.spring.core.naming.component.CustomComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("configuration")
public class MyConfiguration {

    @Bean("qualifierComponent")
    public CustomComponent component() {
        return new CustomComponent();
    }

    @Bean("beanComponent")
    public CustomComponent myComponent() {
        return new CustomComponent();
    }
}
