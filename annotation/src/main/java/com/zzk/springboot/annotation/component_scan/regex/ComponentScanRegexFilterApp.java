package com.zzk.springboot.annotation.component_scan.regex;

import com.zzk.springboot.annotation.component_scan.custom.ComponentScanCustomFilterApp;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@ComponentScan(includeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*(nt)"))
public class ComponentScanRegexFilterApp {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ComponentScanCustomFilterApp.class);
        List<String> beans = Arrays.stream(applicationContext.getBeanDefinitionNames())
                .filter(bean -> !bean.contains("org.springframework") && !bean.contains("componentScanCustomFilterApp")
                        && !bean.contains("componentScanCustomFilter"))
                .collect(Collectors.toList());
        Assert.isTrue(beans.size() == 1);
    }
}
