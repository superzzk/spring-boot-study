package com.zzk.spring.core.profiles;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("com.zzk.spring.core.profiles")
@PropertySource(value = "classpath:application.properties")
public class SpringProfilesConfig {

}
