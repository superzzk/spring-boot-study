package com.zzk.springboot.annotation.qualifer;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class FooServiceImpl implements FooService{
    @Override
    public void foo() {
        return;
    }
}
