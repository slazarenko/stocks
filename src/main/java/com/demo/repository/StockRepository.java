package com.demo.repository;

import com.demo.entity.StockEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StockRepository extends CrudRepository<StockEntity, Long> {
    @Query("select s from Stock s where s.id = :stockId and s.lastUpdate is null")
    StockEntity getLastStock(@Param("stockId") long stockId);

    List<StockEntity> findAllById(long stockId);

    @Query("select s from Stock s where s.lastUpdate is null")
    List<StockEntity> findAllStocks();
}
