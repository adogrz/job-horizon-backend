package com.jobhorizon.backend.ofertatrabajo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Schema(description = "Solicitud para crear o actualizar una oferta de trabajo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfertaTrabajoRequest {

    @Schema(description = "Título del puesto de trabajo (máx. 200 caracteres)", example = "Desarrollador Java Senior", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El título no puede estar vacío")
    @Size(max = 200, message = "El título no puede exceder 200 caracteres")
    private String titulo;

    @Schema(description = "Descripción del puesto de trabajo", example = "Buscamos un desarrollador Java con experiencia en Spring Boot...", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "La descripción no puede estar vacía")
    private String descripcion;

    @Schema(description = "Salario mínimo (opcional)", example = "1500.00")
    @DecimalMin(value = "0.0", message = "El salario mínimo no puede ser negativo")
    private BigDecimal salarioMin;

    @Schema(description = "Salario máximo (opcional)", example = "2500.00")
    @DecimalMin(value = "0.0", message = "El salario máximo no puede ser negativo")
    private BigDecimal salarioMax;

    @Schema(description = "Número de vacantes disponibles", example = "2", defaultValue = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El número de vacantes es obligatorio")
    @Min(value = 1, message = "Debe haber al menos 1 vacante")
    private Short numVacantes;

    @Schema(description = "Años mínimos de experiencia requeridos", example = "3", defaultValue = "0", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Los años de experiencia mínima son obligatorios")
    @Min(value = 0, message = "Los años de experiencia no pueden ser negativos")
    private Short aniosExperienciaMinima;

    @Schema(description = "Fecha de vencimiento de la oferta (yyyy-MM-dd)", example = "2026-07-15", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "La fecha de vencimiento es obligatoria")
    @Future(message = "La fecha de vencimiento debe ser una fecha futura")
    private LocalDate fechaVencimiento;

    @Schema(description = "ID del tipo de contrato", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El tipo de contrato es obligatorio")
    private Integer idTipoContrato;

    @Schema(description = "ID del nivel educativo mínimo requerido", example = "3", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El nivel educativo es obligatorio")
    private Integer idNivelEducativo;

    @Schema(description = "ID de la modalidad de trabajo", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "La modalidad es obligatoria")
    private Integer idModalidad;

    @Schema(description = "ID del distrito de ubicación de la plaza", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El distrito es obligatorio")
    private Integer idDistrito;

    @Schema(description = "Habilidades técnicas requeridas")
    @Builder.Default
    private Set<OfertaHabilidadRequest> habilidades = new HashSet<>();

    @Schema(description = "Idiomas requeridos")
    @Builder.Default
    private Set<OfertaIdiomaRequest> idiomas = new HashSet<>();
}
