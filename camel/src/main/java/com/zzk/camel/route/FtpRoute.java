package com.zzk.camel.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class FtpRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("stream:in?promptMessage=Enter something:")
                .to("ftp://rider:secret@localhost:21000/target/data/outbox");
    }
}
