package com.jobhorizon.backend.postulante.recomendacion.dto;

/**
 * DTO de respuesta para una recomendación del postulante.
 */
public record RecomendacionResponse(
    Integer numRecomendacion,
    String nombreContacto,
    String telefonoContacto,
    Integer idTipoRecomendacion,
    String tipoRecomendacion
) {}
