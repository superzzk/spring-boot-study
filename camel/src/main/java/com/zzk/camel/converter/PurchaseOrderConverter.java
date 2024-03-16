package com.zzk.camel.converter;

import org.apache.camel.Converter;
import org.apache.camel.Exchange;
import org.apache.camel.TypeConverter;

import java.math.BigDecimal;

@Converter
public final class PurchaseOrderConverter {

    @Converter
    public static PurchaseOrder toPurchaseOrder(byte[] data, Exchange exchange) {
        TypeConverter converter = exchange.getContext().getTypeConverter();

        String s = converter.convertTo(String.class, data);
        if (s == null || s.length() < 30) {
            throw new IllegalArgumentException("data is invalid");
        }

        s = s.replaceAll("##START##", "");
        s = s.replaceAll("##END##", "");

        String name = s.substring(0, 10).trim();
        String s2 = s.substring(10, 20).trim();
        String s3 = s.substring(20).trim();

        BigDecimal price = new BigDecimal(s2);
        price.setScale(2);

        Integer amount = converter.convertTo(Integer.class, s3);

        return new PurchaseOrder(name, price, amount);
    }

    @Converter
    public static PurchaseOrder toPurchaseOrder(String data, Exchange exchange) {
        TypeConverter converter = exchange.getContext().getTypeConverter();

        if (data == null || data.length() < 30) {
            throw new IllegalArgumentException("data is invalid");
        }

        data = data.replaceAll("##START##", "");
        data = data.replaceAll("##END##", "");

        String name = data.substring(0, 10).trim();
        String s2 = data.substring(10, 20).trim();
        String s3 = data.substring(20).trim();

        BigDecimal price = new BigDecimal(s2);
        price.setScale(2);

        Integer amount = converter.convertTo(Integer.class, s3);

        return new PurchaseOrder(name, price, amount);
    }

}
