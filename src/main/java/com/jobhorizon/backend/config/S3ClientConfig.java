package com.jobhorizon.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.net.URI;

/**
 * Configuración para crear los beans S3Client y S3Presigner adaptados para Cloudflare R2.
 */
@Configuration
public class S3ClientConfig {

    private final R2Properties r2Properties;

    public S3ClientConfig(R2Properties r2Properties) {
        this.r2Properties = r2Properties;
    }

    @Bean
    public S3Client s3Client() {
        URI endpoint = URI.create("https://" + r2Properties.accountId() + ".r2.cloudflarestorage.com");
        
        return S3Client.builder()
                .endpointOverride(endpoint)
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(r2Properties.accessKey(), r2Properties.secretKey())
                ))
                .region(Region.of("auto")) // R2 requiere la región 'auto'
                .serviceConfiguration(S3Configuration.builder()
                        .pathStyleAccessEnabled(true) // Requerido para compatibilidad R2
                        .chunkedEncodingEnabled(false) // Deshabilitar chunked encoding para R2
                        .build())
                .build();
    }

    @Bean
    public S3Presigner s3Presigner() {
        URI endpoint = URI.create("https://" + r2Properties.accountId() + ".r2.cloudflarestorage.com");

        return S3Presigner.builder()
                .endpointOverride(endpoint)
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(r2Properties.accessKey(), r2Properties.secretKey())
                ))
                .region(Region.of("auto")) // R2 requiere la región 'auto'
                .serviceConfiguration(S3Configuration.builder()
                        .pathStyleAccessEnabled(true)
                        .chunkedEncodingEnabled(false) // Deshabilitar chunked encoding para R2
                        .build())
                .build();
    }
}
