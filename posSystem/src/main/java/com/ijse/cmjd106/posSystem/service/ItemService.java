package com.ijse.cmjd106.posSystem.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ijse.cmjd106.posSystem.dto.ItemRequest;
import com.ijse.cmjd106.posSystem.dto.ItemResponse;

@Service
public interface ItemService {
    ItemResponse createItem(ItemRequest itemRequest);

    List<ItemResponse> getAllItems();

    ItemResponse getItemById(Integer id);

    ItemResponse updateItem(Integer id, ItemRequest itemRequest);

    void deleteCategory(Integer id);

    void updateCurrentQty(Integer itemId,Integer currentQty);
}
