package com.jobhorizon.backend.postulante.experiencia.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

/**
 * DTO de solicitud para crear o actualizar una experiencia laboral.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExperienciaLaboralRequest {

    @NotBlank(message = "El nombre de la empresa es obligatorio")
    @Size(max = 150, message = "El nombre de la empresa no puede exceder 150 caracteres")
    private String nombreEmpresa;

    @NotBlank(message = "El puesto es obligatorio")
    @Size(max = 120, message = "El puesto no puede exceder 120 caracteres")
    private String puesto;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    @NotNull(message = "Debe indicar si es trabajo actual")
    private Boolean trabajoActual;

    @NotBlank(message = "Las funciones son obligatorias")
    private String funciones;

    @Size(max = 15, message = "El teléfono de contacto no puede exceder 15 caracteres")
    private String telefonoContacto;

    @Size(max = 150, message = "El correo de contacto no puede exceder 150 caracteres")
    private String correoContacto;
}
