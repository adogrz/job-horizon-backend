package com.jobhorizon.backend.seguridad.auth.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO para la solicitud de verificación de cuenta por correo electrónico.
 */
public record VerificacionRequest(
        @NotBlank(message = "El token de verificación es obligatorio")
        String token
) {}
