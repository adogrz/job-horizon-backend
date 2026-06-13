package com.jobhorizon.backend.postulante.experiencia.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

/**
 * DTO de solicitud para crear o actualizar una experiencia laboral.
 */
@Schema(description = "Cuerpo de la solicitud para agregar o actualizar una experiencia laboral")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExperienciaLaboralRequest {

    @Schema(description = "Nombre de la empresa o institución (máx. 150 caracteres)", example = "Google LLC", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El nombre de la empresa es obligatorio")
    @Size(max = 150, message = "El nombre de la empresa no puede exceder 150 caracteres")
    private String nombreEmpresa;

    @Schema(description = "Nombre del puesto o cargo desempeñado (máx. 120 caracteres)", example = "Desarrollador Backend Senior", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El puesto es obligatorio")
    @Size(max = 120, message = "El puesto no puede exceder 120 caracteres")
    private String puesto;

    @Schema(description = "Fecha de inicio en formato YYYY-MM-DD", example = "2020-01-15", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    @Schema(description = "Fecha de fin en formato YYYY-MM-DD. Dejar nulo si `trabajoActual` es true", example = "2023-06-30")
    private LocalDate fechaFin;

    @Schema(description = "Indica si este es el trabajo actual. Si es true, `fechaFin` puede ser nulo", example = "false", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Debe indicar si es trabajo actual")
    private Boolean trabajoActual;

    @Schema(description = "Descripción de las funciones y responsabilidades del cargo", example = "Desarrollo de APIs REST con Spring Boot, gestión de base de datos SQL Server, revisión de código.", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Las funciones son obligatorias")
    private String funciones;

    @Schema(description = "Teléfono de contacto de referencia laboral (opcional, máx. 15 caracteres)", example = "987123456")
    @Size(max = 15, message = "El teléfono de contacto no puede exceder 15 caracteres")
    private String telefonoContacto;

    @Schema(description = "Correo electrónico de referencia laboral (opcional, máx. 150 caracteres)", example = "supervisor@google.com")
    @Size(max = 150, message = "El correo de contacto no puede exceder 150 caracteres")
    private String correoContacto;
}
