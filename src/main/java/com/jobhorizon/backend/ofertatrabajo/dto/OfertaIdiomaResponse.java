package com.jobhorizon.backend.ofertatrabajo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "Detalle de un idioma en la oferta de trabajo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfertaIdiomaResponse {
    private Integer idIdioma;
    private String nombreIdioma;
    private Integer idNivelIdioma;
    private String nombreNivelIdioma;
}
