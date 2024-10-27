package com.ijse.cmjd106.posSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemResponse {
    private Integer itemId;
    private String itemName;
    private String description;
    private double unitPrice;
    private Integer currentStock;
    private Integer categoryId;
    private String CategoryName;
}
