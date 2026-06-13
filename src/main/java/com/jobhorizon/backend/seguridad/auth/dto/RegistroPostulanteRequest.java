package com.jobhorizon.backend.seguridad.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Schema(description = "Cuerpo de la solicitud para registrar un nuevo postulante")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroPostulanteRequest {

    @Schema(description = "Correo electrónico del postulante (debe ser único)", example = "juan.perez@correo.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El correo no puede estar vacío")
    @Email(message = "El correo debe ser una dirección de correo válida")
    private String correo;

    @Schema(description = "Contraseña del postulante (mínimo 8 caracteres recomendado)", example = "MiContrasena123!", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password;

    @Schema(description = "Nombres del postulante", example = "Juan Carlos", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Los nombres no pueden estar vacíos")
    private String nombres;

    @Schema(description = "Apellidos del postulante", example = "Pérez García", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Los apellidos no pueden estar vacíos")
    private String apellidos;

    @Schema(description = "Fecha de nacimiento en formato YYYY-MM-DD", example = "1995-06-15", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate fechaNacimiento;

    @Schema(description = "Número de documento de identidad", example = "12345678", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El número de documento es obligatorio")
    private String numDocumento;

    @Schema(description = "Número Único del Contribuyente Profesional (opcional)", example = "NUP001234")
    private String nup;

    @Schema(description = "Número de Identificación Tributaria (opcional)", example = "12345678901")
    private String nit;

    @Schema(description = "Dirección del postulante", example = "Av. Arequipa 1234, Miraflores", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "La dirección no puede estar vacía")
    private String direccion;

    @Schema(description = "ID del género (obtener de /catalogos/generos)", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El género es obligatorio")
    private Integer idGenero;

    @Schema(description = "ID del tipo de documento (obtener de /catalogos/tipos-documento)", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El tipo de documento es obligatorio")
    private Integer idTipoDocumento;

    @Schema(description = "ID del distrito de residencia (obtener de /catalogos/departamentos/{id}/distritos)", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El distrito es obligatorio")
    private Integer idDistrito;
}
