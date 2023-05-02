package com.zzk.springboot.example.di.prototype_injection;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;

import static org.springframework.util.Assert.isTrue;

/**
 * Lookup
 *
 * @author zhangzhongkun02
 * @date 2023/4/30 8:33 PM
 */
@Configuration
@Slf4j
public class Solution2 {
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public PrototypeBean prototypeBean() {
        return new PrototypeBean();
    }

    public static void main(String[] args) {
        final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Solution2.class);

        SingletonBean firstSingleton = ctx.getBean(SingletonBean.class);
        PrototypeBean firstPrototype = firstSingleton.getPrototypeBean();

        // get singleton bean instance one more time
        SingletonBean secondSingleton = ctx.getBean(SingletonBean.class);
        PrototypeBean secondPrototype = secondSingleton.getPrototypeBean();

        isTrue(firstPrototype != secondPrototype);
    }

    @Component
    public class SingletonBean {
        @Autowired
        private PrototypeBean prototypeBean;

        public SingletonBean() {
            log.info("Singleton instance created");
        }

        @Lookup
        public PrototypeBean getPrototypeBean() {
            return null;
        }
    }

    public class PrototypeBean {
        public PrototypeBean() {
            log.info("Prototype bean created.");
        }
    }
}
