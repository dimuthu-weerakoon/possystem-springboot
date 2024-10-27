package com.ijse.cmjd106.posSystem.dto;

import java.util.List;

import com.ijse.cmjd106.posSystem.model.InvoiceLineItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceRequest {

    private Integer invoiceId;
    private String invoiceNumber;
    private String InvoiceType;
    private String referenceNumber;
    private List<InvoiceLineItem> invoiceLineItems;
    private double totalAmount;
    private String paymentStatus;
}
