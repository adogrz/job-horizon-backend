package com.jobhorizon.backend.postulante.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

/**
 * DTO de solicitud para actualizar los datos personales del postulante.
 */
@Schema(description = "Cuerpo de la solicitud para actualizar los datos personales del postulante autenticado")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActualizarDatosPersonalesRequest {

    @Schema(description = "Nombres del postulante (máx. 100 caracteres)", example = "Juan Carlos", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Los nombres no pueden estar vacíos")
    @Size(max = 100, message = "Los nombres no pueden exceder 100 caracteres")
    private String nombres;

    @Schema(description = "Apellidos del postulante (máx. 100 caracteres)", example = "Pérez García", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Los apellidos no pueden estar vacíos")
    @Size(max = 100, message = "Los apellidos no pueden exceder 100 caracteres")
    private String apellidos;

    @Schema(description = "Fecha de nacimiento en formato YYYY-MM-DD", example = "1995-06-15", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate fechaNacimiento;

    @Schema(description = "Número de documento de identidad (máx. 20 caracteres)", example = "12345678", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El número de documento es obligatorio")
    @Size(max = 20, message = "El número de documento no puede exceder 20 caracteres")
    private String numDocumento;

    @Schema(description = "Número Único Previsional (opcional, máx. 20 caracteres)", example = "NUP001234")
    @Size(max = 20, message = "El NUP no puede exceder 20 caracteres")
    private String nup;

    @Schema(description = "Número de Identificación Tributaria (opcional, máx. 20 caracteres)", example = "12345678901")
    @Size(max = 20, message = "El NIT no puede exceder 20 caracteres")
    private String nit;

    @Schema(description = "Dirección de residencia (máx. 300 caracteres)", example = "Av. Arequipa 1234, Miraflores", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "La dirección no puede estar vacía")
    @Size(max = 300, message = "La dirección no puede exceder 300 caracteres")
    private String direccion;

    @Schema(description = "URL de la foto de perfil (opcional, máx. 500 caracteres)", example = "https://storage.jobhorizon.com/fotos/juan.jpg")
    @Size(max = 500, message = "La URL de la foto no puede exceder 500 caracteres")
    private String fotoUrl;

    @Schema(description = "ID del género (obtener de /catalogos/generos)", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El género es obligatorio")
    private Integer idGenero;

    @Schema(description = "ID del tipo de documento (obtener de /catalogos/tipos-documento)", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El tipo de documento es obligatorio")
    private Integer idTipoDocumento;

    @Schema(description = "ID del distrito de residencia (obtener de /catalogos/departamentos/{id}/distritos)", example = "15", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El distrito es obligatorio")
    private Integer idDistrito;
}
