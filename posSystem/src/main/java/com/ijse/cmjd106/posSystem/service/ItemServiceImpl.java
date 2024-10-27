package com.ijse.cmjd106.posSystem.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ijse.cmjd106.posSystem.dto.ItemRequest;
import com.ijse.cmjd106.posSystem.dto.ItemResponse;
import com.ijse.cmjd106.posSystem.model.Category;
import com.ijse.cmjd106.posSystem.model.Item;
import com.ijse.cmjd106.posSystem.repository.CategoryRepository;
import com.ijse.cmjd106.posSystem.repository.ItemRepository;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ItemResponse createItem(ItemRequest itemRequest) {

        Category category = categoryRepository.findById(itemRequest.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category Not Found"));

        Item item = Item.builder()
                .itemName(itemRequest.getItemName())
                .description(itemRequest.getDescription())
                .currentStock(itemRequest.getCurrentStock())
                .unitPrice(itemRequest.getUnitPrice())
                .category(category)
                .build();

        Item createdItem = itemRepository.save(item);
        return maptoItemResponse(createdItem);
    }

    @Override
    public List<ItemResponse> getAllItems() {
        List<Item> items = itemRepository.findAll();
        ArrayList<ItemResponse> itemResponses = new ArrayList<>();

        for (Item item : items) {
            ItemResponse itemResponse = maptoItemResponse(item);
            itemResponses.add(itemResponse);
        }

        return itemResponses;

    }

    @Override
    public ItemResponse getItemById(Integer id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new RuntimeException("Item Not Found"));
        return maptoItemResponse(item);

    }

    @Override
    public ItemResponse updateItem(Integer id, ItemRequest itemRequest) {
        Category category = categoryRepository.findById(itemRequest.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category Not Found"));
        Item existingItem = itemRepository.findById(id).orElseThrow(() -> new RuntimeException("Item Not found"));
        existingItem.setItemName(itemRequest.getItemName());
        existingItem.setDescription(itemRequest.getDescription());
        existingItem.setCurrentStock(itemRequest.getCurrentStock());
        existingItem.setUnitPrice(itemRequest.getUnitPrice());
        existingItem.setCategory(category);

        Item updatedItem = itemRepository.save(existingItem);
        return maptoItemResponse(updatedItem);
    }

    @Override
    public void deleteCategory(Integer id) {
        itemRepository.deleteById(id);
    }

    private ItemResponse maptoItemResponse(Item item) {
        return ItemResponse.builder()
                .itemId(item.getId())
                .itemName(item.getItemName())
                .description(item.getDescription())
                .currentStock(item.getCurrentStock())
                .unitPrice(item.getUnitPrice())
                .categoryId(item.getCategory().getId())
                .CategoryName(item.getCategory().getCategoryName())
                .build();
    }

    @Override
    public void updateCurrentQty(Integer itemId, Integer currentQty) {
        Item existingItem = itemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Item not found"));
        existingItem.setCurrentStock(currentQty);
        itemRepository.save(existingItem);
    }

}
