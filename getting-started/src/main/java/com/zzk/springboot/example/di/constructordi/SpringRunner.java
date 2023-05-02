package com.zzk.springboot.example.di.constructordi;

import com.zzk.springboot.example.di.constructordi.domain.Car;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringRunner {
    public static void main(String[] args) {
        Car toyota = getCarFromXml();

        System.out.println(toyota);

        toyota = getCarFromJavaConfig();

        System.out.println(toyota);
    }

    private static Car getCarFromJavaConfig() {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        return context.getBean(Car.class);
    }

    private static Car getCarFromXml() {
        ApplicationContext context = new ClassPathXmlApplicationContext("di/constructordi.xml");

        return context.getBean(Car.class);
    }
}
