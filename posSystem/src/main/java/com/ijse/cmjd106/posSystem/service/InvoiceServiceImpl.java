package com.ijse.cmjd106.posSystem.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ijse.cmjd106.posSystem.dto.InvoiceLineItemResponse;
import com.ijse.cmjd106.posSystem.dto.InvoiceResponse;
import com.ijse.cmjd106.posSystem.model.Invoice;
import com.ijse.cmjd106.posSystem.model.InvoiceLineItem;
import com.ijse.cmjd106.posSystem.repository.InvoiceRepository;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Override
    public List<InvoiceResponse> getAllInvoiceResponses() {
        List<Invoice> invoices = invoiceRepository.findAll();

        ArrayList<InvoiceResponse> invoiceResponses = new ArrayList<>();
        for (Invoice invoice : invoices) {
            InvoiceResponse invoiceResponse = maptoInvoiceResponse(invoice);
            invoiceResponses.add(invoiceResponse);
        }
        return invoiceResponses;
    }

    @Override
    public InvoiceResponse getInvoiceResponseByRefNo(String refNo) {
        Invoice invoice = invoiceRepository.findByReferenceNumber(refNo);
        return maptoInvoiceResponse(invoice);
    }

    private InvoiceLineItemResponse maptoInvoiceLineItemResponse( InvoiceLineItem invoiceLineItem) {
        InvoiceLineItemResponse invLineItemResponse = new InvoiceLineItemResponse();
        invLineItemResponse.setItemName(invoiceLineItem.getItem().getItemName());
        invLineItemResponse.setQuantity(invoiceLineItem.getQuantity());
        invLineItemResponse.setUnitPrice(invoiceLineItem.getUnitPrice());
        invLineItemResponse.setTotalAmount(invoiceLineItem.getTotalAmount());
        return invLineItemResponse;

    }

    private InvoiceResponse maptoInvoiceResponse(Invoice invoice) {

        ArrayList<InvoiceLineItemResponse> invLineItemsResponses = new ArrayList<>();

        for (InvoiceLineItem invoiceLineItem : invoice.getInvoiceLineItems()) {
            InvoiceLineItemResponse invLineItemResponse = maptoInvoiceLineItemResponse(invoiceLineItem);
            invLineItemsResponses.add(invLineItemResponse);
        }

        return InvoiceResponse.builder()
                .invoiceId(invoice.getId())
                .invoiceNumber(invoice.getInvoiceNumber())
                .paymentStatus(invoice.getPaymentStatus())
                .issuedAt(invoice.getIssuedAt())
                .refNumber(invoice.getReferenceNumber())
                .subTotal(invoice.getTotalAmount())
                .invoiceLineItemResponses(invLineItemsResponses)
                .build();

    }

}
