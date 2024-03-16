package com.zzk.spring.core.di.prototype_injection;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import static org.springframework.util.Assert.isTrue;

/**
 * ApplicationContext
 *
 * @author zhangzhongkun02
 * @date 2023/4/30 8:33 PM
 */
@Configuration
@Slf4j
public class Solution1 {
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
        final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Solution1.class);

        SingletonBean firstSingleton = ctx.getBean(SingletonBean.class);
        PrototypeBean firstPrototype = firstSingleton.getPrototypeBean();

        // get singleton bean instance one more time
        SingletonBean secondSingleton = ctx.getBean(SingletonBean.class);
        PrototypeBean secondPrototype = secondSingleton.getPrototypeBean();

        isTrue(firstPrototype != secondPrototype);
    }

    public class SingletonBean implements ApplicationContextAware {
        ApplicationContext ctx;
        @Autowired
        private PrototypeBean prototypeBean;

        public SingletonBean() {
            log.info("Singleton instance created");
        }

        public PrototypeBean getPrototypeBean() {
            return ctx.getBean(PrototypeBean.class);
        }

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            this.ctx = applicationContext;
        }
    }

    public class PrototypeBean {
        public PrototypeBean() {
            log.info("Prototype bean created.");
        }
    }
}
