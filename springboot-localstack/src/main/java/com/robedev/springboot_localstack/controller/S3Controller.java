package com.robedev.springboot_localstack.controller;

import com.robedev.springboot_localstack.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketResponse;

import java.io.File;

@RestController
@RequestMapping("/api/v1/s3")
public class S3Controller {

    @Autowired
    private S3Service s3Service;

    @Autowired
    private S3Client s3Client;  // Inyectamos el cliente S3 directamente

    // Endpoint para crear el bucket
    @PostMapping("/create-bucket")
    public String createBucket() {
        s3Service.createBucket();
        return "Bucket creado correctamente";
    }

    // Endpoint para subir un archivo
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") File file) {
        s3Service.uploadFile(file);
        return "Archivo subido correctamente";
    }

    // Endpoint para listar los archivos
    @GetMapping("/list")
    public String listFiles() {
        s3Service.listFiles();
        return "Archivos listados correctamente";
    }

    // Endpoint para comprobar si el bucket existe
    @GetMapping("/check-bucket")
    public String checkBucketExists(@RequestParam String bucketName) {
        try {
            // Verificamos si el bucket existe utilizando S3Client
            HeadBucketRequest request = HeadBucketRequest.builder()
                    .bucket(bucketName)
                    .build();

            HeadBucketResponse response = s3Client.headBucket(request);
            return "Bucket " + bucketName + " exists.";
        } catch (Exception e) {
            return "Bucket " + bucketName + " does not exist or cannot be accessed: " + e.getMessage();
        }
    }
}
