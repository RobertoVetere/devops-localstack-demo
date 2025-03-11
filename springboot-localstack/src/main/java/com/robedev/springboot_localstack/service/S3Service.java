package com.robedev.springboot_localstack.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
/*
@Service
public class S3Service {

    private final AmazonS3 s3Client;
    private final String bucketName = "my-bucket";  // El nombre de tu bucket (deberás crearlo primero)

    // Constructor que configura el cliente S3
    public S3Service(@Value("${cloud.aws.endpoint}") String endpoint,
                     @Value("${cloud.aws.credentials.accessKey}") String accessKey,
                     @Value("${cloud.aws.credentials.secretKey}") String secretKey) {
        s3Client = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AmazonS3ClientBuilder.EndpointConfiguration(endpoint, "us-east-1"))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .enablePathStyleAccess()
                .build();
    }

    // Método para crear el bucket
    public void createBucket() {
        if (!s3Client.doesBucketExistV2(bucketName)) {
            s3Client.createBucket(new CreateBucketRequest(bucketName));
        }
    }

    // Método para subir un archivo a S3
    public void uploadFile(File file) {
        s3Client.putObject(new PutObjectRequest(bucketName, file.getName(), file));
    }

    // Método para listar archivos en el bucket
    public void listFiles() {
        ObjectListing objectListing = s3Client.listObjects(new ListObjectsRequest().withBucketName(bucketName));
        for (S3ObjectSummary summary : objectListing.getObjectSummaries()) {
            System.out.println("Object: " + summary.getKey());
        }
    }
}

 */
