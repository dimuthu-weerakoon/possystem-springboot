package com.ijse.cmjd106.posSystem.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.ijse.cmjd106.posSystem.dto.StockLineItemReponse;
import com.ijse.cmjd106.posSystem.dto.StockLineItemRequest;
import com.ijse.cmjd106.posSystem.dto.StockRequest;
import com.ijse.cmjd106.posSystem.dto.StockResponse;
import com.ijse.cmjd106.posSystem.enums.InvoiceType;
import com.ijse.cmjd106.posSystem.model.Invoice;
import com.ijse.cmjd106.posSystem.model.InvoiceLineItem;
import com.ijse.cmjd106.posSystem.model.Item;
import com.ijse.cmjd106.posSystem.model.Stock;
import com.ijse.cmjd106.posSystem.model.StockLineItem;
import com.ijse.cmjd106.posSystem.model.User;
import com.ijse.cmjd106.posSystem.repository.InvoiceRepository;
import com.ijse.cmjd106.posSystem.repository.ItemRepository;
import com.ijse.cmjd106.posSystem.repository.StockLineItemRepository;
import com.ijse.cmjd106.posSystem.repository.StockRepository;
import com.ijse.cmjd106.posSystem.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class StockServiceImpl implements StockService {
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StockLineItemRepository stockLineItemRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Override
    public StockResponse createStock(StockRequest stockRequest) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String username = userDetails.getUsername();
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Stock stock = Stock.builder()
                    .batchNumber("BATCH-" + UUID.randomUUID())
                    .user(user)
                    .build();
            ArrayList<StockLineItem> stockLineItems = new ArrayList<>();

            for (StockLineItemRequest stockLineItemRequest : stockRequest.getStockLineItemRequests()) {
                Item item = itemRepository.findById(stockLineItemRequest.getItemId())
                        .orElseThrow(() -> new RuntimeException("Item not Found"));

                StockLineItem stockLineItem = StockLineItem.builder()
                        .item(item)
                        .quantity(stockLineItemRequest.getQuantity())
                        .currentQuantity(stockLineItemRequest.getQuantity())
                        .unitPrice(stockLineItemRequest.getUnitPrice())
                        .totalAmount(stockLineItemRequest.getQuantity() * stockLineItemRequest.getUnitPrice())
                        .build();
                stockLineItems.add(stockLineItem);

            }
            stock.setStockLineItems(stockLineItems);
            Stock createdStock = stockRepository.save(stock);

            // update item's total current quantity after stock saved

            for (StockLineItem stockLineItem : stockLineItems) {
                updatedItemTotalCurrentStock(stockLineItem.getItem().getId());
            }

                //generate invoice

                generateStockInvoice(createdStock,stockRequest);



            return mapToStockResponse(createdStock);
        } else {
            throw new RuntimeException("User is not authenticated");
        }
    }

    @Override
    public void updatedItemTotalCurrentStock(Integer itemId) {

        Integer updatedItemCurrentStock = stockLineItemRepository.findTotalCurrentQuantityByItemId(itemId);
        if (updatedItemCurrentStock == null) {
            updatedItemCurrentStock = 0;
        }

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));
        item.setCurrentStock(updatedItemCurrentStock);
        itemRepository.save(item);
    }

    @Override
    public void updateStockLineItemCurrentQty(Integer itemId, Integer requestedQuantity) {
        List<Stock> selectedStocks = stockRepository.findLatestStocksByItemId(itemId, requestedQuantity);

        for (Stock stock : selectedStocks) {
            for (StockLineItem stockLineItem : stock.getStockLineItems()) {
                if (stockLineItem.getItem().getId().equals(itemId)) {
                    Integer currentQuantity = stockLineItem.getCurrentQuantity();

                    if (currentQuantity >= requestedQuantity) {

                        stockLineItem.setCurrentQuantity(currentQuantity - requestedQuantity);
                        return;
                    } else {

                        requestedQuantity -= currentQuantity;
                        stockLineItem.setCurrentQuantity(0);
                    }
                }
            }
        }

        if (requestedQuantity > 0) {
            throw new RuntimeException("Insufficient stock quantity available for item ID: " + itemId);
        }
    }

    private StockLineItemReponse mapToStockLineItemResponse(StockLineItem stockLineItem) {
        return StockLineItemReponse.builder()
                .itemId(stockLineItem.getItem().getId())
                .quantity(stockLineItem.getQuantity())
                .currentQuantity(stockLineItem.getCurrentQuantity())
                .unitPrice(stockLineItem.getUnitPrice())
                .build();
    }

    private StockResponse mapToStockResponse(Stock stock) {

        ArrayList<StockLineItemReponse> stockLineItemReponses = new ArrayList<>();

        for (StockLineItem stockLineItem : stock.getStockLineItems()) {
            StockLineItemReponse stockLineItemReponse = mapToStockLineItemResponse(stockLineItem);
            stockLineItemReponses.add(stockLineItemReponse);
        }

        return StockResponse.builder()
                .stockId(stock.getId())
                .batchNumber(stock.getBatchNumber())
                .userId(stock.getUser().getId())
                .username(stock.getUser().getUsername())
                .stockLineItemReponses(stockLineItemReponses)
                .receivedAt(stock.getReceivedAt())
                .build();
    }

    @Override
    public List<StockResponse> getAllStocks() {
        List<Stock> stocks = stockRepository.findAll();
        ArrayList<StockResponse> stockResponsesList = new ArrayList<>();
        for (Stock stock : stocks) {
            StockResponse stockResponse = mapToStockResponse(stock);
            stockResponsesList.add(stockResponse);
        }
        return stockResponsesList;
    }



    private InvoiceLineItem mapToInvoiceLineItem(StockLineItem stockLineItem) {
        return InvoiceLineItem.builder()
                .item(stockLineItem.getItem())
                .quantity(stockLineItem.getQuantity())
                .unitPrice(stockLineItem.getUnitPrice())
                .totalAmount(stockLineItem.getTotalAmount())
                .build();
    }


    private void generateStockInvoice(Stock stock,StockRequest stockRequest) {

        ArrayList<InvoiceLineItem> invoiceLineItems = new ArrayList<>();
        double totalAmount = 0.0;
        for (StockLineItem stockLineItem : stock.getStockLineItems()) {

            InvoiceLineItem invoiceLineItem = mapToInvoiceLineItem(stockLineItem);
            totalAmount += stockLineItem.getTotalAmount();
            invoiceLineItems.add(invoiceLineItem);
        }


        Invoice generatedInvoice = Invoice.builder()
                .invoiceNumber("INV-STOCK-" + UUID.randomUUID())
                .referenceNumber(stock.getBatchNumber())
                .invoiceType(InvoiceType.STOCK)
                .totalAmount(totalAmount)
                .invoiceLineItems(invoiceLineItems)
                .paymentStatus(stockRequest.getPaymentStatus())
                .build();
        invoiceRepository.save(generatedInvoice);

    }
}
