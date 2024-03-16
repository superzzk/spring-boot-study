package com.zzk.spring.core.naming.controller;

import com.zzk.spring.core.naming.service.MessagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MessagingController {

    @Autowired
    private MessagingService service;
}
