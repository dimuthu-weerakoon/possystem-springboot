package com.ijse.cmjd106.posSystem.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ijse.cmjd106.posSystem.dto.InvoiceResponse;

@Service
public interface InvoiceService  {
 List<InvoiceResponse> getAllInvoiceResponses();
 InvoiceResponse getInvoiceResponseByRefNo(String refNo);
}
