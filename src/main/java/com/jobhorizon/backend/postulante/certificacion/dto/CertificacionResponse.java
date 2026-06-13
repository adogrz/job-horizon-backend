package com.jobhorizon.backend.postulante.certificacion.dto;

import java.time.LocalDate;

/**
 * DTO de respuesta para una certificación del postulante.
 */
public record CertificacionResponse(
    Integer codCert,
    String codigoCertificacion,
    String nombre,
    Integer idTipoCertificacion,
    String tipoCertificacion,
    String institucion,
    LocalDate fechaObtencion,
    String archivoUrl
) {}
