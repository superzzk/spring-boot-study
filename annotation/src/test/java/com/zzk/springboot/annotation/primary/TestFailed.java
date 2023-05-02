package com.zzk.springboot.annotation.primary;

import com.zzk.springboot.annotation.primary.failed.Failed;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(classes = Failed.class)
public class TestFailed {

    @Test
    public void test() {
        return;
    }
}
