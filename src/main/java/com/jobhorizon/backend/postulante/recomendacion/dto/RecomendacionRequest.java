package com.jobhorizon.backend.postulante.recomendacion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * DTO de solicitud para crear o actualizar una recomendación.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecomendacionRequest {

    @NotBlank(message = "El nombre del contacto es obligatorio")
    @Size(max = 150, message = "El nombre del contacto no puede exceder 150 caracteres")
    private String nombreContacto;

    @NotBlank(message = "El teléfono del contacto es obligatorio")
    @Size(max = 15, message = "El teléfono del contacto no puede exceder 15 caracteres")
    private String telefonoContacto;

    @NotNull(message = "El tipo de recomendación es obligatorio")
    private Integer idTipoRecomendacion;
}
