package com.jobhorizon.backend.postulante.publicacion.dto;

import java.time.LocalDate;

public record PublicacionResponse(
    Integer numPublicacion,
    String titulo,
    String lugarPublicacion,
    LocalDate fecha,
    String isbn,
    String edicion
) {}
