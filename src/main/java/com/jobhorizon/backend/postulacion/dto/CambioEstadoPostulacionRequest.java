package com.jobhorizon.backend.postulacion.dto;

import jakarta.validation.constraints.NotNull;

/**
 * DTO para solicitar la actualización del estado de una postulación.
 */
public record CambioEstadoPostulacionRequest(
    @NotNull(message = "El ID del estado es obligatorio")
    Integer idEstadoAplicacion
) {}
