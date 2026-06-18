package com.jobhorizon.backend.storage.dto;

/**
 * DTO que contiene la URL de subida pre-firmada y la URL pública final de un archivo.
 */
public record PresignedUrlResponse(
        String uploadUrl,
        String publicUrl
) {}
