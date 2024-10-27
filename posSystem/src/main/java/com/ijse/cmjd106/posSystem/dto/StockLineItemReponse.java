package com.ijse.cmjd106.posSystem.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockLineItemReponse {
    private Integer itemId;
    private Integer quantity;
    private Integer currentQuantity;
    private double unitPrice;
    
}
