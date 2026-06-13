package com.jobhorizon.backend.postulante.formacion.dto;

import java.time.LocalDate;

/**
 * DTO de respuesta para una formación académica del postulante.
 */
public record FormacionAcademicaResponse(
    Integer numFormacion,
    String institucion,
    String titulo,
    Integer idNivelEducativo,
    String nivelEducativo,
    LocalDate fechaInicio,
    LocalDate fechaFin,
    Boolean enCurso
) {}
