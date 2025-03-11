package com.robedev.springboot_localstack.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.regions.Region;
import java.io.File;
import java.net.URI;
import java.nio.file.Paths;

@Service
public class S3Service {

    private final S3Client s3Client;
    private final String bucketName = "my-bucket";  // El nombre de tu bucket

    // Constructor que configura el cliente S3
    public S3Service(@Value("${cloud.aws.s3.endpoint}") String endpoint,
                     @Value("${cloud.aws.credentials.access-key}") String accessKey,
                     @Value("${cloud.aws.credentials.secret-key}") String secretKey) {
        this.s3Client = S3Client.builder()
                .region(Region.US_EAST_1)  // Cambiar la región si es necesario
                .endpointOverride(URI.create("http://localhost:4566"))  // Usar el endpoint correcto
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
    }


    // Método para crear el bucket
    public void createBucket() {
        try {
            if (!doesBucketExist()) {
                CreateBucketRequest createBucketRequest = CreateBucketRequest.builder()
                        .bucket(bucketName)
                        .build();
                s3Client.createBucket(createBucketRequest);
                System.out.println("Bucket creado: " + bucketName);
            } else {
                System.out.println("El bucket ya existe.");
            }
        } catch (SdkException e) {
            System.err.println("Error creando el bucket: " + e.getMessage());
        }
    }

    // Método para comprobar si el bucket existe
    private boolean doesBucketExist() {
        try {
            ListBucketsResponse response = s3Client.listBuckets();
            return response.buckets().stream()
                    .anyMatch(bucket -> bucket.name().equals(bucketName));
        } catch (SdkException e) {
            System.err.println("Error comprobando si el bucket existe: " + e.getMessage());
            return false;
        }
    }

    // Método para subir un archivo a S3
    public void uploadFile(File file) {
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(file.getName())
                    .build();

            s3Client.putObject(putObjectRequest, Paths.get(file.getPath()));
            System.out.println("Archivo subido: " + file.getName());
        } catch (SdkException e) {
            System.err.println("Error subiendo el archivo: " + e.getMessage());
        }
    }

    // Método para listar archivos en el bucket
    public void listFiles() {
        try {
            ListObjectsV2Request listObjectsRequest = ListObjectsV2Request.builder()
                    .bucket(bucketName)
                    .build();

            ListObjectsV2Response listObjectsResponse = s3Client.listObjectsV2(listObjectsRequest);

            listObjectsResponse.contents().forEach(s3Object ->
                    System.out.println("Archivo: " + s3Object.key())
            );
        } catch (SdkException e) {
            System.err.println("Error listando archivos: " + e.getMessage());
        }
    }
}
