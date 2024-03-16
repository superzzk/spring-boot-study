package com.zzk.spring.core.el;

import com.zzk.spring.core.abstract_autowired.DemoApp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Configuration
public class ArithmeticOperators {

    @Test
    public void demo(){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ArithmeticOperators.class);
        final ArithmeticOperators bean = applicationContext.getBean(ArithmeticOperators.class);
        assertEquals(20, bean.add);
        assertEquals(20, bean.multiply);
    }

    // arithmetic_operators
    @Value("#{19 + 1}") // 20
    private double add;

    @Value("#{'String1 ' + 'string2'}") // "String1 string2"
    private String addString;

    @Value("#{20 - 1}") // 19
    private double subtract;

    @Value("#{10 * 2}") // 20
    private double multiply;

    @Value("#{36 / 2}") // 19
    private double divide;

    @Value("#{36 div 2}") // 18, the same as for / operator
    private double divideAlphabetic;

    @Value("#{37 % 10}") // 7
    private double modulo;

    @Value("#{37 mod 10}") // 7, the same as for % operator
    private double moduloAlphabetic;

    @Value("#{2 ^ 9}") // 512
    private double powerOf;

    @Value("#{(2 + 2) * 2 + 9}") // 17
    private double brackets;
}