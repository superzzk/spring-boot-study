package com.zzk.spring.core.beanpostprocessor;

@FunctionalInterface
public interface StockTradeListener {

    void stockTradePublished(StockTrade trade);
}
