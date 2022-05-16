package com.zzk.springboot.example.basic.start_run;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Component
public class MyApplicationRunner implements ApplicationRunner, Ordered {

    @Override
    public int getOrder(){
        return 2;//通过设置这里的数字来知道指定顺序
    }

    @Override
    public void run(ApplicationArguments var1) throws Exception {

        System.out.println("com.zzk.springboot.example.test.start_run.MyApplicationRunner class will be execute when the project was started!");
    }

}
