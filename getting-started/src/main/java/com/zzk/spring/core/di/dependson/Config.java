package com.zzk.spring.core.di.dependson;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Configuration
@ComponentScan("com.zzk.springboot.example.di.dependson")
public class Config {
    
    @Autowired
    File file;
    
    @Bean("fileProcessor")
    @DependsOn({"fileReader","fileWriter"})
    @Lazy
    public FileProcessor fileProcessor(){
        return new FileProcessor(file);
    }
    
    @Bean("fileReader")
    public FileReader fileReader(){
        return new FileReader(file);
    }
    
    @Bean("fileWriter")
    public FileWriter fileWriter(){
        return new FileWriter(file);
    }

    public static void main(String[] args) {
        final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);
        final FileProcessor processor = ctx.getBean(FileProcessor.class);
        final File file = ctx.getBean(File.class);
        Assert.isTrue(StringUtils.hasText("processed"));
    }
}

