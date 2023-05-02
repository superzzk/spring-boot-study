package com.zzk.springboot.example.di.circulardependency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 *
 * @author zhangzhongkun02
 * @date 2023/5/2 2:00 PM
 */
@Configuration
public class Problem {

    @Component
    public class CircularDependencyA {

        private CircularDependencyB circB;

        @Autowired
        public CircularDependencyA(CircularDependencyB circB) {
            this.circB = circB;
        }
    }

    @Component
    public class CircularDependencyB {

        private CircularDependencyA circA;

        @Autowired
        public CircularDependencyB(CircularDependencyA circA) {
            this.circA = circA;
        }
    }

    public static void main(String[] args) {
        // 异常
        final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Problem.class);

    }
}
