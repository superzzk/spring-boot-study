package com.zzk.springboot.annotation;

import com.zzk.springboot.annotation.qualifer.FooService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Application.class)
public class QualifierTestFailed {
    @Autowired
    FooService fooService;

    // 会抛出NoUniqueBeanDefinitionException
    @Test
    public void test() {
        return;
    }
}
