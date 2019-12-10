package com.pritamprasad.productservice.controller;

import com.pritamprasad.productservice.models.Product;
import com.pritamprasad.productservice.repo.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
public class ProductsController {

    @Autowired
    private ProductsRepository productsRepository;

    @GetMapping("/products")
    public ResponseEntity<Page<Product>> getItems(@Valid Product product, Pageable pageable) {
        if(product != null){
            return ResponseEntity.ok(productsRepository.findAll(Example.of(product),pageable));
        }
        return ResponseEntity.ok(productsRepository.findAll(pageable));
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
