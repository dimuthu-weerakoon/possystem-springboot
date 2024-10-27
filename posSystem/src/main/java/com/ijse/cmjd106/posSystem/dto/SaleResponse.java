package com.ijse.cmjd106.posSystem.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleResponse {
    private Integer saleId;
    private String saleNumber;
    private Integer userId;
    private String username;
    private LocalDateTime createdAt;
    private List<SaleLineItemReponse> saleLineItemReponses;
    private double totalAmount;
}
