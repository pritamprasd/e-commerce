package com.pritamprasad.productservice.controller;

import com.pritamprasad.productservice.models.Product;
import com.pritamprasad.productservice.repo.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ProductsController {

    @Autowired
    private ProductsRepository productsRepository;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getItems() {
        return ResponseEntity.ok(productsRepository.findAll());
    }

    @PostMapping("/products")
    public ResponseEntity<Product> addItem(@RequestBody Product products) {
        return ResponseEntity.ok(productsRepository.save(products));
    }

    @PutMapping("/products")
    public ResponseEntity<Product> updateItem(@RequestBody Product products) {
        if (!productsRepository.existsById(products.getProductId())) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productsRepository.save(products));
    }

    @DeleteMapping("/products/{productid}")
    public ResponseEntity removeItem(@PathVariable("productid") UUID id) {
        productsRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
