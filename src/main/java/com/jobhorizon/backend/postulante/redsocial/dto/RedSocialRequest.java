package com.jobhorizon.backend.postulante.redsocial.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * DTO de solicitud para agregar o actualizar una red social del postulante.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RedSocialRequest {

    @NotNull(message = "El tipo de red social es obligatorio")
    private Integer idTipoRedSocial;

    @NotBlank(message = "La URL no puede estar vacía")
    @Size(max = 500, message = "La URL no puede exceder 500 caracteres")
    private String url;
}
