package com.jobhorizon.backend.ofertatrabajo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "Detalle de una habilidad en la oferta de trabajo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfertaHabilidadResponse {
    private Integer idHabilidad;
    private String nombreHabilidad;
    private Integer idNivelHabilidad;
    private String nombreNivelHabilidad;
}
