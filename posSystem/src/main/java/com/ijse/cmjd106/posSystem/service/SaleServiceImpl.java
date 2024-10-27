package com.ijse.cmjd106.posSystem.service;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.ijse.cmjd106.posSystem.dto.SaleLineItemReponse;
import com.ijse.cmjd106.posSystem.dto.SaleLineItemRequest;
import com.ijse.cmjd106.posSystem.dto.SaleRequest;
import com.ijse.cmjd106.posSystem.dto.SaleResponse;
import com.ijse.cmjd106.posSystem.enums.InvoiceType;
import com.ijse.cmjd106.posSystem.model.Invoice;
import com.ijse.cmjd106.posSystem.model.InvoiceLineItem;
import com.ijse.cmjd106.posSystem.model.Item;
import com.ijse.cmjd106.posSystem.model.Sale;
import com.ijse.cmjd106.posSystem.model.SaleLineItem;
import com.ijse.cmjd106.posSystem.model.User;
import com.ijse.cmjd106.posSystem.repository.InvoiceRepository;
import com.ijse.cmjd106.posSystem.repository.ItemRepository;
import com.ijse.cmjd106.posSystem.repository.SaleRepository;
import com.ijse.cmjd106.posSystem.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SaleServiceImpl implements SaleService {
    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StockService stockService;
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Override
    public SaleResponse createSale(SaleRequest saleRequest) {
        if (saleRequest.getSaleLineItemRequests() == null || saleRequest.getSaleLineItemRequests().isEmpty()) {
            throw new IllegalArgumentException("Sale line items cannot be null or empty");
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || !(auth.getPrincipal() instanceof UserDetails)) {
            throw new RuntimeException("User is not authenticated");
        }

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Sale sale = Sale.builder()
                .saleNumber("SALE-" + UUID.randomUUID().toString())
                .user(user)
                .build();

        double totalAmount = 0.0;
        ArrayList<SaleLineItem> saleLineItems = new ArrayList<>();

        for (SaleLineItemRequest saleLineItemRequest : saleRequest.getSaleLineItemRequests()) {
            Item item = itemRepository.findById(saleLineItemRequest.getItemId())
                    .orElseThrow(() -> new RuntimeException("Item not found"));

            // Check stock availability
            if (item.getCurrentStock() < saleLineItemRequest.getQuantity()) {
                throw new RuntimeException("Insufficient stock for item: " + item.getItemName());
            }

            double saleLineItemTotalAmount = saleLineItemRequest.getQuantity() * item.getUnitPrice();
            SaleLineItem saleLineItem = SaleLineItem.builder()
                    .item(item)
                    .quantity(saleLineItemRequest.getQuantity())
                    .totalAmount(saleLineItemTotalAmount)
                    .build();

            saleLineItems.add(saleLineItem);
            totalAmount += saleLineItemTotalAmount;
        }

        sale.setSaleLineItems(saleLineItems);
        sale.setTotalAmount(totalAmount);

        // Save sale
        Sale createdSale = saleRepository.save(sale);

        // Update stock quantities
        for (SaleLineItem saleLineItem : saleLineItems) {
            try {
                stockService.updateStockLineItemCurrentQty(saleLineItem.getItem().getId(), saleLineItem.getQuantity());
                stockService.updatedItemTotalCurrentStock(saleLineItem.getItem().getId());
            } catch (Exception e) {
                throw new RuntimeException("Failed to update stock for item: " + saleLineItem.getItem().getItemName(),
                        e);
            }
        }

        generateSaleInvoice(createdSale,saleRequest);

        return mapToSaleResponse(createdSale);
    }

    private SaleLineItemReponse mapToSaleLineItemResponse(SaleLineItem saleLineItem) {
        return SaleLineItemReponse.builder()
                .itemId(saleLineItem.getItem().getId())
                .quantity(saleLineItem.getQuantity())
                .unitPrice(saleLineItem.getItem().getUnitPrice())
                .totalAmount(saleLineItem.getTotalAmount())
                .build();
    }

    private SaleResponse mapToSaleResponse(Sale sale) {

        ArrayList<SaleLineItemReponse> saleLineItemReponses = new ArrayList<>();

        for (SaleLineItem saleLineItem : sale.getSaleLineItems()) {
            SaleLineItemReponse saleLineItemReponse = mapToSaleLineItemResponse(saleLineItem);
            saleLineItemReponses.add(saleLineItemReponse);
        }
        return SaleResponse.builder()
                .saleId(sale.getId())
                .saleNumber(sale.getSaleNumber())
                .userId(sale.getUser().getId())
                .username(sale.getUser().getUsername())
                .saleLineItemReponses(saleLineItemReponses)
                .totalAmount(sale.getTotalAmount())
                .createdAt(sale.getCreatedAt())
                .build();
    }

    private InvoiceLineItem mapToInvoiceLineItem(SaleLineItem saleLineItem) {
        return InvoiceLineItem.builder()
                .item(saleLineItem.getItem())
                .quantity(saleLineItem.getQuantity())
                .unitPrice(saleLineItem.getItem().getUnitPrice())
                .totalAmount(saleLineItem.getTotalAmount())
                .build();
    }

    private void generateSaleInvoice(Sale sale,SaleRequest saleRequest) {

        ArrayList<InvoiceLineItem> invoiceLineItems = new ArrayList<>();

        for (SaleLineItem saleLineItem : sale.getSaleLineItems()) {

            InvoiceLineItem invoiceLineItem = mapToInvoiceLineItem(saleLineItem);
            invoiceLineItems.add(invoiceLineItem);
        }

        Invoice generatedInvoice =  Invoice.builder()
                .invoiceNumber("INV-SALE-" + UUID.randomUUID())
                .referenceNumber(sale.getSaleNumber())
                .invoiceType(InvoiceType.SALE)
                .totalAmount(sale.getTotalAmount())
                .invoiceLineItems(invoiceLineItems)
                .paymentStatus(saleRequest.getPaymentStatus())
                .build();
        invoiceRepository.save(generatedInvoice);
        
    }
}