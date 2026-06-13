package com.jobhorizon.backend.postulante.logro.dto;

import java.time.LocalDate;

/**
 * DTO de respuesta para un logro del postulante.
 */
public record LogroResponse(
    Integer numLogro,
    String descripcion,
    LocalDate fecha
) {}
