package com.jobhorizon.backend.postulante.experiencia.dto;

import java.time.LocalDate;

/**
 * DTO de respuesta para una experiencia laboral del postulante.
 */
public record ExperienciaLaboralResponse(
    Integer numExp,
    String nombreEmpresa,
    String puesto,
    LocalDate fechaInicio,
    LocalDate fechaFin,
    Boolean trabajoActual,
    String funciones,
    String telefonoContacto,
    String correoContacto
) {}
