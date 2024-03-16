package com.zzk.spring.core.di.prototype_injection;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.function.Function;

import static org.springframework.util.Assert.isTrue;

/**
 * TODO:
 *
 * @author zhangzhongkun02
 * @date 2023/4/30 8:33 PM
 */
@Configuration
@Slf4j
public class Solution4 {
    @Bean
    public Function<String, PrototypeBean> beanFactory() {
        return name -> prototypeBeanWithParam(name);
    }

    @Bean
    @Scope(value = "prototype")
    public PrototypeBean prototypeBeanWithParam(String name) {
        return new PrototypeBean(name);
    }

    public static void main(String[] args) {
        final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Solution4.class);

        SingletonFunctionBean firstSingleton = ctx.getBean(SingletonFunctionBean.class);
        PrototypeBean firstPrototype = firstSingleton.getPrototypeInstance("no1");

        // get singleton bean instance one more time
        SingletonFunctionBean secondSingleton = ctx.getBean(SingletonFunctionBean.class);
        PrototypeBean secondPrototype = secondSingleton.getPrototypeInstance("no2");

        isTrue(firstPrototype != secondPrototype);
    }

    @Component
    public class SingletonFunctionBean {
        @Autowired
        private Function<String, PrototypeBean> beanFactory;

        public PrototypeBean getPrototypeInstance(String name) {
            PrototypeBean bean = beanFactory.apply(name);
            return bean;
        }
    }

    public class PrototypeBean {
        private String name ;
        public PrototypeBean(String name) {
            this.name = name;
            log.info("Prototype bean "+ name +" created.");
        }
    }
}
