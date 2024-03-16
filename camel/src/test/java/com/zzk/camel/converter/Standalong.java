package com.zzk.camel.converter;

import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class Standalong {
    @Test
    void testPurchaseOrderConverter() throws Exception {
        CamelContext context = new DefaultCamelContext();
        context.getTypeConverterRegistry().addTypeConverters(new PurchaseOrderConverter());
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:start").convertBodyTo(PurchaseOrder.class);
            }
        });
        context.start();

        ProducerTemplate template = context.createProducerTemplate();

//        byte[] data = "##START##AKC4433   179.95    3##END##".getBytes();
        String data = "##START##AKC4433   179.95    3##END##";
        PurchaseOrder order = template.requestBody("direct:start", data, PurchaseOrder.class);
        assertNotNull(order);

        System.out.println(order);

        assertEquals("AKC4433", order.getName());
        assertEquals("179.95", order.getPrice().toString());
        assertEquals(3, order.getAmount());
    }

}
