package com.zzk.springboot.example.di.circulardependency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 *
 * @author zhangzhongkun02
 * @date 2023/5/2 2:00 PM
 */
@Configuration
public class Solution2 {

    @Component
    public class A {
        public B circB;

        @Autowired
        public A(B circB) {
            this.circB = circB;
        }
    }

    @Component
    public class B {
        private A circA;

        @Autowired
        public B(@Lazy A circA) {
            this.circA = circA;
        }
    }

    public static void main(String[] args) {
        final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Solution2.class);
        final B b = ctx.getBean(B.class);
        final A a = ctx.getBean(A.class);
        Assert.isTrue(a.circB == b);
    }
}
