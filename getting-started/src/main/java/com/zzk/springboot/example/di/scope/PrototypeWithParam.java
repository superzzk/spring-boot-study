package com.zzk.springboot.example.di.scope;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.util.Assert;

@Configuration
public class PrototypeWithParam {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Person getPerson(String name) {
        return new Person(name);
    }

    @Bean
    public Child getChild() {
        return new Child();
    }

    class Person{
        @Autowired
        public Child child;

        public String name;

        public Person(String name) {
            this.name = name;
        }
    }

    public static void main(String[] args) {
        final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(PrototypeWithParam.class);
        final PrototypeWithParam config = ctx.getBean(PrototypeWithParam.class);
        final Person person1 = config.getPerson("name1");
        final Person person2 = config.getPerson("name2");
        Assert.isTrue(person1.name.equals("name1"));
        Assert.isTrue(person2.name.equals("name2"));
        Assert.isTrue(person1 != person2);

        Assert.isTrue(person1.child == person2.child);
    }

    private class Child {
    }
}
