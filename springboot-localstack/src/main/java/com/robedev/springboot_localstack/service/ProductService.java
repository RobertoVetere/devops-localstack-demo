package com.robedev.springboot_localstack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.Map;

@Service
public class ProductService {

    private final DynamoDbClient dynamoDbClient;

    @Autowired
    public ProductService(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    public void createTable() {
        CreateTableRequest request = CreateTableRequest.builder()
                .tableName("Products")
                .keySchema(KeySchemaElement.builder().attributeName("ProductID").keyType(KeyType.HASH).build())
                .attributeDefinitions(
                        AttributeDefinition.builder().attributeName("ProductID").attributeType(ScalarAttributeType.S).build())
                .provisionedThroughput(ProvisionedThroughput.builder().readCapacityUnits(5L).writeCapacityUnits(5L).build())
                .build();

        dynamoDbClient.createTable(request);
        System.out.println("Table Created Successfully");
    }

    public void insertProduct(String productId, String productName) {
        PutItemRequest request = PutItemRequest.builder()
                .tableName("Products")
                .item(Map.of("ProductID", AttributeValue.builder().s(productId).build(),
                        "ProductName", AttributeValue.builder().s(productName).build()))
                .build();

        dynamoDbClient.putItem(request);
    }

    public String getProduct(String productId) {
        GetItemRequest request = GetItemRequest.builder()
                .tableName("Products")
                .key(Map.of("ProductID", AttributeValue.builder().s(productId).build()))
                .build();

        GetItemResponse response = dynamoDbClient.getItem(request);
        if (response.hasItem()) {
            return response.item().get("ProductName").s();
        } else {
            return "Product not found";
        }
    }
}
