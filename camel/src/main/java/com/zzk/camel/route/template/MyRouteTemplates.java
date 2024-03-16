package com.zzk.camel.route.template;

import org.apache.camel.builder.RouteBuilder;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Route templates using {@link RouteBuilder} which allows
 * us to define a number of templates (parameterized routes)
 * which we can create routes from.
 */
@Component
//@Profile("java")
public class MyRouteTemplates extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // in this example we have created the template in XML and this is disabled
        // create a route template with the given name
        routeTemplate("myTemplate")
            // here we define the required input parameters (can have default values)
            .templateParameter("name")
            .templateParameter("greeting")
            .templateParameter("myPeriod", "3s")
            // here comes the route in the template
            // notice how we use {{name}} to refer to the template parameters
            // we can also use {{propertyName}} to refer to property placeholders
            .from("timer:{{name}}?period={{myPeriod}}")
                .setBody(simple("{{greeting}} {{name}}"))
                .log("Java says ${body}");
    }
}
