package com.zzk.springboot.example.di.prototype_injection;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.util.Assert.isTrue;

/**
 * ObjectFactory
 *
 * @author zhangzhongkun02
 * @date 2023/4/30 8:33 PM
 */
@Configuration
@Slf4j
public class Solution3 {
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public PrototypeBean prototypeBean() {
        return new PrototypeBean();
    }

    public static void main(String[] args) {
        final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Solution3.class);

        SingletonBean firstSingleton = ctx.getBean(SingletonBean.class);
        PrototypeBean firstPrototype = firstSingleton.getPrototypeBean();

        // get singleton bean instance one more time
        SingletonBean secondSingleton = ctx.getBean(SingletonBean.class);
        PrototypeBean secondPrototype = secondSingleton.getPrototypeBean();

        isTrue(firstPrototype != secondPrototype, "The same instance should be returned");
    }

    @Component
    public class SingletonBean {
        @Autowired
        private PrototypeBean prototypeBean;

        @Autowired
        private ObjectFactory<PrototypeBean> provider;

        public SingletonBean() {
            log.info("Singleton instance created");
        }

        public PrototypeBean getPrototypeBean() {
            return provider.getObject();
        }
    }

    public class PrototypeBean {
        public PrototypeBean() {
            log.info("Prototype bean created.");
        }
    }
}
