package com.ijse.cmjd106.posSystem.dto;


import java.util.List;

import com.ijse.cmjd106.posSystem.enums.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockRequest {
 
    
    private List<StockLineItemRequest> stockLineItemRequests;
    private PaymentStatus paymentStatus;
}
