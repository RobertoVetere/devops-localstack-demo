package com.robedev.springboot_localstack.model;

 // Lombok genera el constructor sin parámetros // Lombok genera el constructor con todos los parámetros
public class Product {
    private String productId;  // productId
    private String productName;  // productName

    // Constructor con parámetros manualmente, si es necesario
    public Product(String productId, String productName) {
        this.productId = productId;
        this.productName = productName;
    }

     public String getProductId() {
         return productId;
     }

     public void setProductId(String productId) {
         this.productId = productId;
     }

     public String getProductName() {
         return productName;
     }

     public void setProductName(String productName) {
         this.productName = productName;
     }
 }
