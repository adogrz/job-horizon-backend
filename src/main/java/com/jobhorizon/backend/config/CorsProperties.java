package com.jobhorizon.backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Propiedades de configuración para CORS.
 *
 * <p>Se leen desde el prefijo {@code jobhorizon.cors} en {@code application.yml}.</p>
 *
 * @param allowedOrigins los orígenes permitidos separados por comas
 */
@ConfigurationProperties(prefix = "jobhorizon.cors")
public record CorsProperties(String allowedOrigins) {
}
