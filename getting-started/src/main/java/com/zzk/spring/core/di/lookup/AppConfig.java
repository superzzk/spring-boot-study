package com.zzk.spring.core.di.lookup;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan
public class AppConfig {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Strategy getStrategy(String name) {
        switch (name){
            case "A":
                return new Strategy.StrategyA();
            case "B":
                return new Strategy.StrategyB();
            default:
                throw new IllegalArgumentException("Invalid strategy name: " + name);
        }
    }
}
