package com.jobhorizon.backend.postulante.habilidad.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Schema(description = "Cuerpo de la solicitud para agregar o actualizar una habilidad en el perfil del postulante")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostulanteHabilidadRequest {

    @Schema(description = "ID de la habilidad (obtener de /catalogos/categorias-habilidad/{id}/habilidades)", example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Integer idHabilidad;

    @Schema(description = "ID del nivel de dominio de la habilidad (obtener de /catalogos/niveles-habilidad)", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Integer idNivelHabilidad;
}
