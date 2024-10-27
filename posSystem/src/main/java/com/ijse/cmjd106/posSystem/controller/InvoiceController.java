package com.ijse.cmjd106.posSystem.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ijse.cmjd106.posSystem.dto.InvoiceResponse;
import com.ijse.cmjd106.posSystem.service.InvoiceService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;

    @GetMapping
    public ResponseEntity<List<InvoiceResponse>> getAllInvoice() {
        List<InvoiceResponse> invoiceResponses = invoiceService.getAllInvoiceResponses();
        return ResponseEntity.status(HttpStatus.OK).body(invoiceResponses);
    }

    @GetMapping("/{refNo}")
    public ResponseEntity<InvoiceResponse> getInvoiceByRefNo(@PathVariable String refNo) {
        InvoiceResponse invoiceResponse = invoiceService.getInvoiceResponseByRefNo(refNo);
        return ResponseEntity.status(HttpStatus.OK).body(invoiceResponse);
    }

}
