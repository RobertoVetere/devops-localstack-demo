package com.robedev.springboot_localstack.controller;

import com.robedev.springboot_localstack.model.Product;
import com.robedev.springboot_localstack.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/get-all-products")
    public ResponseEntity<List<Product>> getAllProducts() {
        // Obtener la lista de todos los productos desde el servicio
        List<Product> products = productService.getAllProducts();

        // Si la lista está vacía, devolver un 404 (no encontrado), si no devolver los productos con un 200 OK
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return ResponseEntity.ok(products);
        }
    }
}

