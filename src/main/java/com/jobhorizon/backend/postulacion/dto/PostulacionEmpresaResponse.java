package com.jobhorizon.backend.postulacion.dto;

import java.time.LocalDateTime;

/**
 * DTO que representa la postulación de un candidato a una oferta, vista por la empresa.
 */
public record PostulacionEmpresaResponse(
    Integer idPostulante,
    String nombreCompleto,
    String correo,
    String telefonoPrincipal,
    LocalDateTime fechaAplicacion,
    Integer idEstadoAplicacion,
    String estadoAplicacion
) {}
