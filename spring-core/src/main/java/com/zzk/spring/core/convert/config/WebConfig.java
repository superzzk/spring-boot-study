package com.zzk.spring.core.convert.config;

import com.zzk.spring.core.convert.converter.GenericBigDecimalConverter;
import com.zzk.spring.core.convert.converter.StringToEmployeeConverter;
import com.zzk.spring.core.convert.converter.StringToEnumConverterFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	 
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToEmployeeConverter());
        registry.addConverterFactory(new StringToEnumConverterFactory());
        registry.addConverter(new GenericBigDecimalConverter());
    }
}

