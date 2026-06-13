package com.jobhorizon.backend.postulante.formacion.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

/**
 * DTO de solicitud para crear o actualizar una formación académica.
 */
@Schema(description = "Cuerpo de la solicitud para agregar o actualizar una formación académica")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FormacionAcademicaRequest {

    @Schema(description = "Nombre de la institución educativa (máx. 200 caracteres)", example = "Pontificia Universidad Católica del Perú", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "La institución es obligatoria")
    @Size(max = 200, message = "La institución no puede exceder 200 caracteres")
    private String institucion;

    @Schema(description = "Título o grado obtenido o en curso (máx. 200 caracteres)", example = "Ingeniería de Sistemas", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El título es obligatorio")
    @Size(max = 200, message = "El título no puede exceder 200 caracteres")
    private String titulo;

    @Schema(description = "ID del nivel educativo (obtener de /catalogos/niveles-educativos)", example = "3", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El nivel educativo es obligatorio")
    private Integer idNivelEducativo;

    @Schema(description = "Fecha de inicio en formato YYYY-MM-DD", example = "2015-03-01", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    @Schema(description = "Fecha de fin en formato YYYY-MM-DD. Dejar nulo si `enCurso` es true", example = "2020-12-15")
    private LocalDate fechaFin;

    @Schema(description = "Indica si actualmente sigue cursando esta formación. Si es true, `fechaFin` puede ser nulo", example = "false", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Debe indicar si está en curso")
    private Boolean enCurso;
}
