package com.zzk.camel.route.template;

import org.apache.camel.builder.RouteBuilder;

import org.springframework.context.annotation.Profile;

@org.springframework.stereotype.Component
//@Profile("java")
public class MyTemplateBuilder extends RouteBuilder {

    /**
     * Configure and adds routes from route templates.
     */
    @Override
    public void configure() throws Exception {
        // to configure route templates we can use java code as below from a template builder class,
        // gives more power as its java code.
        // or we can configure as well from application.yaml,
        // less power as its key value pair yaml
        // and you can also use both java and yaml together

        // in this example we use properties by default and have disabled java
        templatedRoute("myTemplate")
            .parameter("name", "one")
            .parameter("greeting", "Hello");

        templatedRoute("myTemplate")
            .parameter("name", "deux")
            .parameter("greeting", "Bonjour")
            .parameter("myPeriod", "5s");
    }
}
