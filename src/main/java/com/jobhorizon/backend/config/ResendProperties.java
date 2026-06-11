package com.jobhorizon.backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Propiedades de configuración para el servicio de envío de correos Resend.
 *
 * <p>Se leen desde el prefijo {@code jobhorizon.resend} en {@code application.yml}.
 * La API key debe cargarse desde la variable de entorno {@code RESEND_API_KEY}.</p>
 *
 * @param apiKey    clave de API de Resend ({@code re_...})
 * @param fromEmail dirección de correo del remitente (ej: {@code noreply@job-horizon.com})
 */
@ConfigurationProperties(prefix = "jobhorizon.resend")
public record ResendProperties(String apiKey, String fromEmail) {}
