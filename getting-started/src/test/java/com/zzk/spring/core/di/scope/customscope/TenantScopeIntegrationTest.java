package com.zzk.spring.core.di.scope.customscope;

import com.zzk.spring.core.di.scope.customscope.TenantBean;
import com.zzk.spring.core.di.scope.customscope.TenantBeansConfig;
import com.zzk.spring.core.di.scope.customscope.TenantScopeConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TenantScopeIntegrationTest {

    @Test
    final void whenRegisterScopeAndBeans_thenContextContainsFooAndBar() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(TenantScopeConfig.class);
        ctx.register(TenantBeansConfig.class);
        ctx.refresh();

        test(ctx);
    }

    @Test
    final void whenComponentScan_thenContextContainsFooAndBar() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.scan("com.zzk.springboot.example.di.scope.customscope");
        ctx.refresh();

        test(ctx);
    }

    private static void test(AnnotationConfigApplicationContext ctx) {
        TenantBean foo = (TenantBean) ctx.getBean("foo", TenantBean.class);
        foo.sayHello();
        TenantBean bar = (TenantBean) ctx.getBean("bar", TenantBean.class);
        bar.sayHello();
        assertThat(foo, not(equalTo(bar)));

        Map<String, TenantBean> foos = ctx.getBeansOfType(TenantBean.class);
        assertThat(foos.size(), equalTo(2));
        assertTrue(foos.containsValue(foo));
        assertTrue(foos.containsValue(bar));

        BeanDefinition fooDefinition = ctx.getBeanDefinition("foo");
        BeanDefinition barDefinition = ctx.getBeanDefinition("bar");

        assertThat(fooDefinition.getScope(), equalTo("tenant"));
        assertThat(barDefinition.getScope(), equalTo("tenant"));
    }
}
