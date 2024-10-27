package com.ijse.cmjd106.posSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class InvoiceLineItemResponse {
    private String itemName;
    private Integer quantity;
    private double unitPrice;
    private double totalAmount;
}
