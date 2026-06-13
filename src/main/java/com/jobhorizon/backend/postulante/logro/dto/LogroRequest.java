package com.jobhorizon.backend.postulante.logro.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

/**
 * DTO de solicitud para crear o actualizar un logro.
 */
@Schema(description = "Cuerpo de la solicitud para agregar o actualizar un logro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogroRequest {

    @Schema(description = "Descripción del logro obtenido", example = "Primer lugar en el Hackathon Nacional de Innovación Tecnológica 2022", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @Schema(description = "Fecha en que se obtuvo el logro en formato YYYY-MM-DD", example = "2022-11-05", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;
}
