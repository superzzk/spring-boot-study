package org.example.service.impl;

import org.example.entity.Stock;
import org.example.repository.StockRepository;
import org.example.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockRepository repository;

    /*
    * 初始化库存
    * 在表中存储一条id为1，库存为1000的记录
    * */
    @Override
    public void init() {
        final Stock s = repository.findById(1L).orElse(new Stock("test", 1000));
        s.setNumber(1000);
        s.setVersion(0);
        repository.save(s);
    }

    /*
    * 库存扣减
    * */
    @Override
    public boolean decreaseSimple(Long id, long count) {
        final Optional<Stock> stock = repository.findById(id);
        if (stock.isPresent() && stock.get().getNumber() >= count) {
            repository.decrease(id, count);
            return true;
        }
        return false;
    }

    private boolean decreaseWithOptimisticLockInner(Long id, long count){
        final Optional<Stock> stock = repository.findById(id);
        if (stock.isPresent() && stock.get().getNumber() >= count) {
            if(repository.decreaseWithOptimisticByVersion(id, count, stock.get().getVersion()) == 1) {
                return true;
            }else{
                System.out.println("decreaseWithOptimisticLock fail!");
            }
        }
        return false;
    }

    @Override
    public boolean decreaseWithOptimisticLock(Long id, long count) {
        int retryCnt = 0;
        boolean rt = false;
        while(retryCnt++<3 && !rt){
            rt = decreaseWithOptimisticLockInner(id, count);
        }
        return rt;
    }


}
