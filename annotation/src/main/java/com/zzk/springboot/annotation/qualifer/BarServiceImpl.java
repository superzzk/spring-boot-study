package com.zzk.springboot.annotation.qualifer;

import org.springframework.stereotype.Component;

@Component
public class BarServiceImpl implements FooService{

    @Override
    public void foo() {
        return;
    }
}
