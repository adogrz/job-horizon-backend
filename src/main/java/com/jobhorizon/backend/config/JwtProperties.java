package com.jobhorizon.backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Propiedades de configuración para la generación y validación de JWT.
 *
 * <p>Se leen desde el prefijo {@code jobhorizon.jwt} en {@code application.yml}.
 * El secreto debe tener mínimo 64 caracteres y cargarse desde variable de entorno
 * {@code JWT_SECRET}.</p>
 *
 * @param secret          clave secreta HMAC-SHA256 para firmar los tokens
 * @param expirationHours horas de validez del JWT a partir de su emisión
 */
@ConfigurationProperties(prefix = "jobhorizon.jwt")
public record JwtProperties(String secret, int expirationHours) {}
