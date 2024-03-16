package com.zzk.camel.route.configuration;

import org.apache.camel.builder.RouteConfigurationBuilder;
import org.springframework.stereotype.Component;

// Use @Component to let spring boot discover this class automatic
@Component
public class MyJavaErrorHandler extends RouteConfigurationBuilder {

    @Override
    public void configuration() throws Exception {
        routeConfiguration("javaError")
            .onException(Exception.class).handled(true)
            .log("Java WARN: ${exception.message}");
    }
}
