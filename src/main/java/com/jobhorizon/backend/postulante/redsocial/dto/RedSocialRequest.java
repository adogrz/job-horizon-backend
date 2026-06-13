package com.jobhorizon.backend.postulante.redsocial.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * DTO de solicitud para agregar o actualizar una red social del postulante.
 */
@Schema(description = "Cuerpo de la solicitud para agregar o actualizar una red social en el perfil")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RedSocialRequest {

    @Schema(description = "ID del tipo de red social (obtener de /catalogos/tipos-red-social)", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El tipo de red social es obligatorio")
    private Integer idTipoRedSocial;

    @Schema(description = "URL del perfil en la red social (máx. 500 caracteres)", example = "https://www.linkedin.com/in/juan-perez", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "La URL no puede estar vacía")
    @Size(max = 500, message = "La URL no puede exceder 500 caracteres")
    private String url;
}
