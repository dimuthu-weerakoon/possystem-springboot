package com.ijse.cmjd106.posSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ijse.cmjd106.posSystem.dto.ItemRequest;
import com.ijse.cmjd106.posSystem.dto.ItemResponse;
import com.ijse.cmjd106.posSystem.service.ItemService;

@RestController
@RequestMapping("/items")
@CrossOrigin( origins = "*")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @GetMapping
    public ResponseEntity<List<ItemResponse>> getAllItems() {
        List<ItemResponse> itemResponse = itemService.getAllItems();
        return ResponseEntity.status(HttpStatus.OK).body(itemResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponse> getItem(@PathVariable Integer id) {
        ItemResponse itemResponse = itemService.getItemById(id);
        return ResponseEntity.status(HttpStatus.OK).body(itemResponse);
    }

    @PostMapping
    public ResponseEntity<ItemResponse> createItem(@RequestBody ItemRequest itemRequest) {
        ItemResponse itemResponse = itemService.createItem(itemRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(itemResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemResponse> updateItem(@PathVariable Integer id, @RequestBody ItemRequest itemRequest) {
        ItemResponse itemResponse = itemService.updateItem(id, itemRequest);
        return ResponseEntity.status(HttpStatus.OK).body(itemResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body("Item Deleted");
    }

}
