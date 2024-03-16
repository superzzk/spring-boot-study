package com.zzk.spring.core.naming.service;

import com.zzk.spring.core.naming.component.CustomComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class MessagingServiceImpl implements MessagingService {

    @Autowired
    @Qualifier("qualifierComponent")
    private CustomComponent customComponent;
}
