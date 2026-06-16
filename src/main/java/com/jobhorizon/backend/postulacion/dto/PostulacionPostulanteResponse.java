package com.jobhorizon.backend.postulacion.dto;

import java.time.LocalDateTime;

/**
 * DTO que representa la postulación vista desde el perfil del postulante.
 */
public record PostulacionPostulanteResponse(
    Integer idOferta,
    String tituloOferta,
    String nombreEmpresa,
    LocalDateTime fechaAplicacion,
    Integer idEstadoAplicacion,
    String estadoAplicacion
) {}
