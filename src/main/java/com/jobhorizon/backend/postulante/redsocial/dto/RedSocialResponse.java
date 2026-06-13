package com.jobhorizon.backend.postulante.redsocial.dto;

/**
 * DTO de respuesta para una red social del postulante.
 */
public record RedSocialResponse(
    Integer idTipoRedSocial,
    String tipoRedSocial,
    String url
) {}
