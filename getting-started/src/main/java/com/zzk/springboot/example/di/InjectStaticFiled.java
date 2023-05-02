package com.zzk.springboot.example.di;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.Assert;

/**
 *
 * @author zhangzhongkun02
 * @date 2023/5/2 1:15 PM
 */
@Configuration
@PropertySource("classpath:application.properties")
public class InjectStaticFiled {

    @Value("${custom-name}")
    public String name;

    @Value("${custom-name}")
    public static String injectStaticFailed;

    public static String injectStaticSuccess;

    @Value("${custom-name}")
    public void setStaticField(String name) {
        InjectStaticFiled.injectStaticSuccess = name;
    }

    public static void main(String[] args) {
        final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(InjectStaticFiled.class);
        final InjectStaticFiled bean = ctx.getBean(InjectStaticFiled.class);
        System.out.println(bean.name);
        Assert.isNull(bean.injectStaticFailed);
        Assert.isTrue(bean.injectStaticSuccess.equals(bean.name));
    }
}
