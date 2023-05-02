package com.zzk.springboot.example.di.spring;

public class MessageApp {

    private IService iService;

    public MessageApp(IService iService) {
        this.iService = iService;
    }

    public String getServiceValue() {
        return iService.serve();
    }
}
