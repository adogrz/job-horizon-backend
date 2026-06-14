package com.jobhorizon.backend.ofertatrabajo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Schema(description = "Requisito de idioma para la oferta de trabajo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfertaIdiomaRequest {

    @Schema(description = "ID del idioma", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El ID del idioma es obligatorio")
    private Integer idIdioma;

    @Schema(description = "ID del nivel de idioma mínimo requerido", example = "3", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El ID del nivel de idioma es obligatorio")
    private Integer idNivelIdioma;
}
