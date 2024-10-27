package com.ijse.cmjd106.posSystem.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ijse.cmjd106.posSystem.dto.StockRequest;
import com.ijse.cmjd106.posSystem.dto.StockResponse;


@Service
public interface StockService {
    StockResponse createStock(StockRequest stockRequest);
    List<StockResponse> getAllStocks();
    void updateStockLineItemCurrentQty(Integer itemId, Integer quantity);
    public void updatedItemTotalCurrentStock(Integer itemid);
}
