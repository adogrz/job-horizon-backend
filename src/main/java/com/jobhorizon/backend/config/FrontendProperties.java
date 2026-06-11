package com.jobhorizon.backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Propiedades de configuración para la URL base del frontend.
 *
 * <p>Se leen desde el prefijo {@code jobhorizon.frontend} en {@code application.yml}.
 * Se usa para construir links absolutos en correos electrónicos (ej: tokens de desbloqueo).</p>
 *
 * @param baseUrl URL base del frontend (ej: {@code https://job-horizon.com})
 */
@ConfigurationProperties(prefix = "jobhorizon.frontend")
public record FrontendProperties(String baseUrl) {}
