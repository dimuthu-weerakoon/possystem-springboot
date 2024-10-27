package com.ijse.cmjd106.posSystem.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ijse.cmjd106.posSystem.dto.SaleRequest;
import com.ijse.cmjd106.posSystem.dto.SaleResponse;
import com.ijse.cmjd106.posSystem.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/sales")
public class SaleController {
  @Autowired
  private SaleService saleService;

  @PostMapping
  public ResponseEntity<SaleResponse> createSale(@RequestBody SaleRequest saleRequest) {
    SaleResponse saleResponse = saleService.createSale(saleRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(saleResponse);
  }

}
