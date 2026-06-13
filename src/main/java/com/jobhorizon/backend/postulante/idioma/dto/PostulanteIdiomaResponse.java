package com.jobhorizon.backend.postulante.idioma.dto;

public record PostulanteIdiomaResponse(
    Integer idIdioma,
    String nombreIdioma,
    Integer idNivelLectura,
    String nombreNivelLectura,
    Integer idNivelEscritura,
    String nombreNivelEscritura,
    Integer idNivelConversacion,
    String nombreNivelConversacion,
    Integer idNivelEscucha,
    String nombreNivelEscucha
) {}
