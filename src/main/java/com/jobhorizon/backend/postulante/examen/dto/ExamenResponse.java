package com.jobhorizon.backend.postulante.examen.dto;

import java.time.LocalDate;

public record ExamenResponse(
    Integer numExamen,
    String tipo,
    String resultado,
    LocalDate fecha,
    String archivoUrl
) {}
