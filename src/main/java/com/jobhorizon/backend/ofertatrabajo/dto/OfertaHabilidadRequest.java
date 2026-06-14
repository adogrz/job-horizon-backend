package com.jobhorizon.backend.ofertatrabajo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Schema(description = "Requisito de habilidad para la oferta de trabajo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfertaHabilidadRequest {

    @Schema(description = "ID de la habilidad", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El ID de la habilidad es obligatorio")
    private Integer idHabilidad;

    @Schema(description = "ID del nivel de habilidad requerido", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El ID del nivel de habilidad es obligatorio")
    private Integer idNivelHabilidad;
}
