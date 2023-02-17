package org.example.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StockServiceTest {
    @Autowired
    private StockService stockService;
    private long orderNo;

    /*
    * 对库存1000件的商品，多线程模拟1200次下单操作，如果没有对库存进行原子性保证，会造成超卖
    * */
    @Test
    public void demo_when_not_atomic_then_oversell() throws InterruptedException {
        stockService.init();

        final ExecutorService es = Executors.newCachedThreadPool();
        for(int i=0;i<1200;i++)
        es.submit(()-> {
            mockOrder(1L);
        });
        es.awaitTermination(10, TimeUnit.SECONDS);
        System.out.println("order count: " + orderNo);
        // order count greater than max stock
        Assertions.assertTrue(orderNo > 1000);
    }

    /*
     * 对库存1000件的商品，多线程模拟1200次下单操作，使用乐观锁控制库存，重试3次，会有大量订单失败
     * */
    @Test
    public void demo_when_optimistic_lock_then_oversell() throws InterruptedException {
        stockService.init();

        final ExecutorService es = Executors.newCachedThreadPool();
        for(int i=0;i<1200;i++)
            es.submit(()-> {
                if (stockService.decreaseWithOptimisticLock(1L, 1)) {
                    orderNo++;
                }
            });
        es.shutdown();
        es.awaitTermination(100, TimeUnit.SECONDS);
        System.out.println("order count: " + orderNo);
        // order count greater than max stock
        Assertions.assertTrue(orderNo < 1000);
    }

    private void mockOrder(Long stockId){
        if (stockService.decreaseSimple(stockId, 1)) {
            orderNo++;
        }
    }
}
