package org.example.repository;

import org.example.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface StockRepository extends JpaRepository<Stock, Long> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE stocks SET number=number-?2 WHERE id=?1", nativeQuery = true)
    int decrease(Long id, long count);

    @Transactional
    @Modifying
    @Query(value = "UPDATE stocks SET number=number-?2 WHERE id=?1 and number=?3", nativeQuery = true)
    int decreaseWithOptimisticLock(Long id, long count, long oldNumber);

    @Transactional
    @Modifying
    @Query(value = "UPDATE stocks SET number=number-?2, version=version+1 WHERE id=?1 AND number>0 AND version=?3",nativeQuery = true)
    int decreaseWithOptimisticByVersion(Long id, long count, long version);
}
