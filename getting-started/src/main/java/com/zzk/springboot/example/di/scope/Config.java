package com.zzk.springboot.example.di.scope;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.util.Assert;

@Configuration
public class Config {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public Person getPerson1() {
        return new Person();
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Person getPerson2() {
        return new Person();
    }

    @Bean
    public Child getChild() {
        return new Child();
    }

    class Person{
        @Autowired
        public Child child;

        public String name;
    }

    public static void main(String[] args) {
        final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);
        final Config config = ctx.getBean(Config.class);
        final Person person1 = config.getPerson1();
        final Person person2 = config.getPerson1();
        Assert.isTrue(person1 == person2);

        final Person person21 = config.getPerson2();
        final Person person22 = config.getPerson2();
        Assert.isTrue(person22 != person21);

        Assert.isTrue(person1.child == person21.child);
    }

    private class Child {
    }
}
