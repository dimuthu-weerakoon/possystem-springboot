package com.ijse.cmjd106.posSystem.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountResponse {
    private String discountType;
    private double discountValue;
}
