package com.ijse.cmjd106.posSystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ijse.cmjd106.posSystem.model.StockLineItem;

@Repository
public interface StockLineItemRepository extends JpaRepository<StockLineItem, Integer> {
    Optional<StockLineItem> findByItemId(Integer itemId);

    @Query("SELECT SUM(currentQuantity) FROM StockLineItem WHERE item.id = ?1")
    Integer findTotalCurrentQuantityByItemId(Integer itemId);

    

}
