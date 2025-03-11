package com.robedev.springboot_localstack.controller;

import com.robedev.springboot_localstack.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create-table")
    public ResponseEntity<String> createTable() {
        productService.createTable();
        return ResponseEntity.status(HttpStatus.CREATED).body("Table created");
    }

    @PostMapping("/insert")
    public ResponseEntity<String> insertProduct(@RequestParam String productId, @RequestParam String productName) {
        productService.insertProduct(productId, productName);
        return ResponseEntity.status(HttpStatus.CREATED).body("Product inserted");
    }

    @GetMapping("/get")
    public ResponseEntity<String> getProduct(@RequestParam String productId) {
        String product = productService.getProduct(productId);
        return ResponseEntity.ok(product);
    }
}

