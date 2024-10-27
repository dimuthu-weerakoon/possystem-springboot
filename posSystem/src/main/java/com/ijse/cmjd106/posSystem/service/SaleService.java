package com.ijse.cmjd106.posSystem.service;

import org.springframework.stereotype.Service;

import com.ijse.cmjd106.posSystem.dto.SaleRequest;
import com.ijse.cmjd106.posSystem.dto.SaleResponse;

@Service
public interface SaleService {
    SaleResponse createSale(SaleRequest saleRequest);
   
}
