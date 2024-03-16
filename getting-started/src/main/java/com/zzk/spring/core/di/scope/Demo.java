package com.zzk.spring.core.di.scope;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Configuration
public class Demo {

    @Data
    @NoArgsConstructor
    static class Person{
        @Autowired
        public ChildSingleton childSingleton;

        @Autowired
        public ChildPrototype childPrototype;

        public String name;
        public Person(String name) {
            this.name = name;
        }
    }
    private static class ChildSingleton { }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    private static class ChildPrototype { }

    @Bean("person1")
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public Person getPersonSingleton() {
        return new Person();
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Person getPersonPrototype() {
        return new Person();
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Person getPersonWithParam(String name) {
        return new Person(name);
    }

    @Bean
    public ChildSingleton getChild() {
        return new ChildSingleton();
    }

    public static void main(String[] args) {
        final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Demo.class);
        singleton(ctx);
        prototype(ctx);
        prototype_with_parameter(ctx);
        autowire_prototype(ctx);
    }

    public static void singleton(AnnotationConfigApplicationContext ctx) {
        final Demo demo = ctx.getBean(Demo.class);
        final Person person1 = demo.getPersonSingleton();
        final Person person2 = demo.getPersonSingleton();
        Assert.isTrue(person1 == person2);

        // get by name
        final Object person11 = ctx.getBean("person1");
        Assert.isTrue(person1 == person11);
    }

    public static void prototype(AnnotationConfigApplicationContext ctx) {
        final Demo demo = ctx.getBean(Demo.class);
        final Person person21 = demo.getPersonPrototype();
        final Person person22 = demo.getPersonPrototype();

        Assert.isTrue(person22 != person21);
        Assert.isTrue(person21.childSingleton == person22.childSingleton);
    }


    public static void prototype_with_parameter(AnnotationConfigApplicationContext ctx){
        final Demo demo = ctx.getBean(Demo.class);
        final Person person1 = demo.getPersonWithParam("name1");
        final Person person2 = demo.getPersonWithParam("name2");
        Assert.isTrue(person1.name.equals("name1"));
        Assert.isTrue(person2.name.equals("name2"));
        Assert.isTrue(person1 != person2);
    }

    public static void autowire_prototype(AnnotationConfigApplicationContext ctx) {
        final Demo demo = ctx.getBean(Demo.class);
        final Person person1 = demo.getPersonPrototype();
        final Person person2 = demo.getPersonPrototype();
        Assert.isTrue(person1 != person2);
        Assert.isTrue(person1.getChildPrototype() != person2.getChildPrototype());
    }
}
