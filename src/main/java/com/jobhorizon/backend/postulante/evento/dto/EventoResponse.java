package com.jobhorizon.backend.postulante.evento.dto;

import java.time.LocalDate;

/**
 * DTO de respuesta para un evento del postulante.
 */
public record EventoResponse(
    Integer numEvento,
    String nombreEvento,
    String lugar,
    String anfitrion,
    LocalDate fecha,
    Integer idTipoParticipacion,
    String tipoParticipacion,
    Integer idPais,
    String pais
) {}
