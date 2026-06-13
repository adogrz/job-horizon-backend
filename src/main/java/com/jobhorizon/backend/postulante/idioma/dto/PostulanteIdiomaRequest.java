package com.jobhorizon.backend.postulante.idioma.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostulanteIdiomaRequest {
    @NotNull
    private Integer idIdioma;

    @NotNull
    private Integer idNivelLectura;

    @NotNull
    private Integer idNivelEscritura;

    @NotNull
    private Integer idNivelConversacion;

    @NotNull
    private Integer idNivelEscucha;
}
