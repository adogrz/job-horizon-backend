package com.jobhorizon.backend.postulante.habilidad.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostulanteHabilidadRequest {
    @NotNull
    private Integer idHabilidad;

    @NotNull
    private Integer idNivelHabilidad;
}
