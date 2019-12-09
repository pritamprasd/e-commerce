package com.pritamprasad.itemservice.controller;

import com.pritamprasad.itemservice.models.Item;
import com.pritamprasad.itemservice.repo.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@RestController
public class ItemsController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/items")
    public ResponseEntity<List<Item>> getItems() {
        return ResponseEntity.ok(itemRepository.findAll());
    }

    @PostMapping("/items")
    public ResponseEntity<Item> addItem(@RequestBody Item item) {
        return ResponseEntity.ok(itemRepository.save(item));
    }

    @PutMapping("/items")
    public ResponseEntity<Item> updateItem(@RequestBody Item item) {
        if (!itemRepository.existsById(item.getItemId())) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(itemRepository.save(item));
    }

    @DeleteMapping("/items/{itemid}")
    public ResponseEntity removeItem(@PathVariable("itemid") UUID id) {
        itemRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
