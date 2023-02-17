package org.example.service;

public interface StockService {

    void init();

    boolean decreaseSimple(Long id, long count);

    boolean decreaseWithOptimisticLock(Long id, long count);
}
