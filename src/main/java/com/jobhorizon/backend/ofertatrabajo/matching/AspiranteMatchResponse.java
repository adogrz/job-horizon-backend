package com.jobhorizon.backend.ofertatrabajo.matching;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

/**
 * DTO que representa el resultado del motor de matching para un aspirante.
 *
 * <p>Los campos se mapean directamente a las columnas devueltas por
 * {@code sp_ObtenerAspirantes}. El puntaje de matching es calculado por
 * {@code fn_PuntajeMatching} en la base de datos y refleja la fórmula
 * ponderada: {@code (α×H + β×A + γ×E + δ×I) × 100}.</p>
 */
@Schema(description = "Resultado de matching de un aspirante para una oferta de trabajo")
public record AspiranteMatchResponse(

        @Schema(description = "ID del usuario/aspirante")
        Integer idUsuario,

        @Schema(description = "Nombres del aspirante")
        String nombres,

        @Schema(description = "Apellidos del aspirante")
        String apellidos,

        @Schema(description = "Nombre completo (nombres + apellidos)")
        String nombreCompleto,

        @Schema(description = "Correo electrónico del aspirante")
        String correo,

        @Schema(description = "Nombre del departamento donde reside el aspirante")
        String departamento,

        @Schema(description = "Nombre del distrito donde reside el aspirante")
        String distrito,

        @Schema(description = "Cantidad de habilidades requeridas que el aspirante cumple con el nivel mínimo")
        Integer habilidadesCoinciden,

        @Schema(description = "Total de habilidades requeridas por la oferta")
        Integer habilidadesRequeridas,

        @Schema(description = "Cantidad de idiomas requeridos que el aspirante cumple en las 4 destrezas")
        Integer idiomasCoinciden,

        @Schema(description = "Total de idiomas requeridos por la oferta")
        Integer idiomasRequeridos,

        @Schema(description = "Puntaje de matching calculado por fn_PuntajeMatching (0.00 – 100.00)")
        BigDecimal puntajeMatching

) {}
