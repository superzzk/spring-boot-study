package com.zzk.springboot.example.di.prototype_injection;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import static org.springframework.util.Assert.isTrue;

/**
 * TODO:
 *
 * @author zhangzhongkun02
 * @date 2023/4/30 8:33 PM
 */
@Configuration
@Slf4j
public class Problem {
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public PrototypeBean prototypeBean() {
        return new PrototypeBean();
    }

    @Bean
    public SingletonBean singletonBean() {
        return new SingletonBean();
    }

    public static void main(String[] args) {
        final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Problem.class);

        SingletonBean firstSingleton = ctx.getBean(SingletonBean.class);
        PrototypeBean firstPrototype = firstSingleton.getPrototypeBean();

        // get singleton bean instance one more time
        SingletonBean secondSingleton = ctx.getBean(SingletonBean.class);
        PrototypeBean secondPrototype = secondSingleton.getPrototypeBean();

        // 向singleton中注入prototype每次都是同一个
        isTrue(firstPrototype == secondPrototype, "The same instance should be returned");
    }

    public class SingletonBean {
        // ..
        @Autowired
        private PrototypeBean prototypeBean;

        public SingletonBean() {
            log.info("Singleton instance created");
        }

        public PrototypeBean getPrototypeBean() {
            return prototypeBean;
        }
    }

    public class PrototypeBean {
        public PrototypeBean() {
            log.info("Prototype bean created.");
        }
    }
}
