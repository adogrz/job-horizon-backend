package com.jobhorizon.backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Propiedades de configuración para Cloudflare R2 (compatible con S3).
 * Se enlazan desde el prefijo {@code jobhorizon.r2} en {@code application.yml}.
 */
@ConfigurationProperties(prefix = "jobhorizon.r2")
public record R2Properties(
        String accessKey,
        String secretKey,
        String accountId,
        String bucketName,
        String publicUrl
) {}
