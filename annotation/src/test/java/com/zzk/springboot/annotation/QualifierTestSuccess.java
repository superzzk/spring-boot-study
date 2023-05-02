package com.zzk.springboot.annotation;

import com.zzk.springboot.annotation.qualifer.Success;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Success.class)
public class QualifierTestSuccess {

    @Test
    public void test() {
        return;
    }
}
