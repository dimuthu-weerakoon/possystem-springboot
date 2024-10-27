package com.ijse.cmjd106.posSystem.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ijse.cmjd106.posSystem.dto.StockRequest;
import com.ijse.cmjd106.posSystem.dto.StockResponse;
import com.ijse.cmjd106.posSystem.service.StockService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/stocks")
public class StockController {
  @Autowired
  private StockService stockService;

  @PostMapping
  public ResponseEntity<StockResponse> createStock(@RequestBody StockRequest stockRequest) {
    StockResponse stockResponse = stockService.createStock(stockRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(stockResponse);

  }

  @GetMapping
  public ResponseEntity<List<StockResponse>> getAllStocks() {
    List<StockResponse> stockResponses = stockService.getAllStocks();
    return ResponseEntity.status(HttpStatus.OK).body(stockResponses);
  }

}
