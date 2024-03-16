package com.zzk.spring.core.di.circulardependency;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 *
 * @author zhangzhongkun02
 * @date 2023/5/2 2:00 PM
 */
@Configuration
public class Solution4 {
    @Component
    public class CircularDependencyA implements ApplicationContextAware, InitializingBean {

        private CircularDependencyB circB;
        private ApplicationContext context;
        public CircularDependencyB getCircB() {
            return circB;
        }

        @Override
        public void afterPropertiesSet() throws Exception {
            circB = context.getBean(CircularDependencyB.class);
        }
        @Override
        public void setApplicationContext(final ApplicationContext ctx) throws BeansException {
            context = ctx;
        }
    }

    @Component
    public class CircularDependencyB {
        private CircularDependencyA circA;
        private String message = "Hi!";

        @Autowired
        public void setCircA(CircularDependencyA circA) {
            this.circA = circA;
        }
        public String getMessage() {
            return message;
        }
    }


    public static void main(String[] args) {
        final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Solution4.class);
    }
}
