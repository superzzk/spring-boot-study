package com.zzk.spring.core.di.lookup;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

@Component
public class Processor {
    @Lookup
    Strategy getStrategy(String name){
        return null;
    }

    public String process(String strategyName) {
        Strategy strategy = getStrategy(strategyName);
        return strategy.execute();
    }
}
