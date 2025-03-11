package com.robedev.springboot_localstack.config;

import org.springframework.beans.factory.annotation.Value;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

@Configuration
public class S3Config {

    @Value("${cloud.aws.s3.endpoint}")
    private String s3Endpoint;

    // Configuración del cliente S3 para LocalStack
    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create("mockAccessKey", "mockSecretKey")
                ))
                .endpointOverride(URI.create(s3Endpoint))  // LocalStack endpoint
                .build();
    }
}
