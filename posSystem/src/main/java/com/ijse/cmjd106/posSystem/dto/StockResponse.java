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
public class StockResponse {
    private Integer stockId;
    private String batchNumber;
    private Integer userId;
    private String username;
    private LocalDateTime receivedAt;
    private List<StockLineItemReponse> stockLineItemReponses;
}
