package com.zzk.springboot.example.di.spring;

public class IndexService implements IService {

    @Override
    public String serve() {
        return "Hello World";
    }

}
