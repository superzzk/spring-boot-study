package com.zzk.springboot.annotation.component_scan.assignable;

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
@ComponentScan(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = Animal.class))
public class ComponentScanAssignableTypeFilterApp {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ComponentScanAssignableTypeFilterApp.class);
        List<String> beans = Arrays.stream(applicationContext.getBeanDefinitionNames())
                .filter(bean -> !bean.contains("org.springframework") && !bean.contains("componentScanAssignableTypeFilterApp"))
                .collect(Collectors.toList());
        Assert.isTrue(beans.size() == 2);
    }
}
