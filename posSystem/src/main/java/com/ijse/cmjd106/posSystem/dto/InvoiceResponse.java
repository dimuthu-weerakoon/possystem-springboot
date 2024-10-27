package com.ijse.cmjd106.posSystem.dto;

import java.time.LocalDateTime;
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
public class InvoiceResponse {
    private Integer invoiceId;
    private String invoiceNumber;
    private PaymentStatus paymentStatus;
    private LocalDateTime issuedAt;
    private String refNumber;
    private List<InvoiceLineItemResponse> invoiceLineItemResponses;
    private double subTotal;
}
