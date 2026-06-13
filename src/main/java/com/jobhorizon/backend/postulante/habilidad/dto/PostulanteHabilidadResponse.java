package com.jobhorizon.backend.postulante.habilidad.dto;

public record PostulanteHabilidadResponse(
    Integer idHabilidad,
    String nombreHabilidad,
    Integer idNivelHabilidad,
    String nombreNivelHabilidad
) {}
