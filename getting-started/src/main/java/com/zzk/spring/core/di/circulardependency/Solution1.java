package com.zzk.spring.core.di.circulardependency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 *
 * @author zhangzhongkun02
 * @date 2023/5/2 2:00 PM
 */
@Configuration
public class Solution1 {

    @Component
    public class A {
        public B circB;

        @Autowired
        public void setB(B b) {
            this.circB = b;
        }
    }

    @Component
    public class B {
        private A circA;

        @Autowired
        public void setA(A a) {
            this.circA = a;
        }
    }

    public static void main(String[] args) {
        final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Solution1.class);
        final B b = ctx.getBean(B.class);
        final A a = ctx.getBean(A.class);
        Assert.isTrue(a.circB == b);
    }
}
