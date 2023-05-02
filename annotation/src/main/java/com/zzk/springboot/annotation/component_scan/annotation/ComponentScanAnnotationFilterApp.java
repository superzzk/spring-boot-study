package com.zzk.springboot.annotation.component_scan.annotation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@ComponentScan(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Animal.class))
public class ComponentScanAnnotationFilterApp {

    public static void main(String[] args) {
        final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ComponentScanAnnotationFilterApp.class);
        List<String> beans = Arrays.stream(ctx.getBeanDefinitionNames())
                .filter(bean -> !bean.contains("org.springframework") && !bean.contains("componentScanAnnotationFilterApp"))
                .collect(Collectors.toList());
        Assert.isTrue(beans.size() == 1);
        Assert.isTrue(beans.get(0).equals("elephant"));
    }
}
