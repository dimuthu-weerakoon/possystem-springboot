package com.ijse.cmjd106.posSystem.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ijse.cmjd106.posSystem.model.Stock;
@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {
     Optional<Stock> findByBatchNumber(String batchNumber);
     
     @Query("SELECT s FROM Stock s JOIN s.stockLineItems sli WHERE sli.item.id = ?1 AND sli.currentQuantity >= ?2 ORDER BY s.receivedAt DESC")
     List<Stock> findLatestStocksByItemId(Integer itemId, Integer quantity);
     
    
}
