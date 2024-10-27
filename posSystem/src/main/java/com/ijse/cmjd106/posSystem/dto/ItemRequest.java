package com.ijse.cmjd106.posSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemRequest {
    private String itemName;
    private String description;
    private double unitPrice;
    private Integer currentStock;
    private Integer categoryId;
}
