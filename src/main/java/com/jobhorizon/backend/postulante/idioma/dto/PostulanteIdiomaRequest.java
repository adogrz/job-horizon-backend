package com.jobhorizon.backend.postulante.idioma.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Schema(description = "Cuerpo de la solicitud para agregar o actualizar un idioma en el perfil del postulante. Se deben indicar los cuatro niveles de competencia.")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostulanteIdiomaRequest {

    @Schema(description = "ID del idioma (obtener de /catalogos/idiomas)", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Integer idIdioma;

    @Schema(description = "ID del nivel de lectura en este idioma (obtener de /catalogos/niveles-idioma)", example = "3", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Integer idNivelLectura;

    @Schema(description = "ID del nivel de escritura en este idioma (obtener de /catalogos/niveles-idioma)", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Integer idNivelEscritura;

    @Schema(description = "ID del nivel de conversación en este idioma (obtener de /catalogos/niveles-idioma)", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Integer idNivelConversacion;

    @Schema(description = "ID del nivel de escucha en este idioma (obtener de /catalogos/niveles-idioma)", example = "3", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Integer idNivelEscucha;
}
