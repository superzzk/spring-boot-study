package com.zzk.spring.core.naming.configuration;

import com.zzk.spring.core.naming.service.AuditService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuditConfiguration {

    @Bean
    public AuditService audit() {
        return new AuditService();
    }
}
