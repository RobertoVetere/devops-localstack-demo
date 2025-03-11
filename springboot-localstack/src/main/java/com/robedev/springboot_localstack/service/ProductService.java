package com.robedev.springboot_localstack.service;

import com.robedev.springboot_localstack.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.ArrayList;
import java.util.List;
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

    public List<Product> getAllProducts() {
        // Crear la solicitud para escanear la tabla
        ScanRequest scanRequest = ScanRequest.builder()
                .tableName("Products")
                .build();

        // Obtener la respuesta de DynamoDB
        ScanResponse scanResponse = dynamoDbClient.scan(scanRequest);

        // Crear una lista para los productos
        List<Product> products = new ArrayList<>();

        // Iterar sobre los elementos escaneados y agregar a la lista
        for (Map<String, AttributeValue> item : scanResponse.items()) {
            String productId = item.get("ProductID").s();  // Obtén el productId
            String productName = item.get("ProductName").s();  // Obtén el productName

            // Crear un producto y agregarlo a la lista
            products.add(new Product(productId, productName));  // Agrega productId
        }

        return products;
    }
}
