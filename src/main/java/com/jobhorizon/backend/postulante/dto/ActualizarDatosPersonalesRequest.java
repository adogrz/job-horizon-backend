package com.jobhorizon.backend.postulante.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

/**
 * DTO de solicitud para actualizar los datos personales del postulante.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActualizarDatosPersonalesRequest {

    @NotBlank(message = "Los nombres no pueden estar vacíos")
    @Size(max = 100, message = "Los nombres no pueden exceder 100 caracteres")
    private String nombres;

    @NotBlank(message = "Los apellidos no pueden estar vacíos")
    @Size(max = 100, message = "Los apellidos no pueden exceder 100 caracteres")
    private String apellidos;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate fechaNacimiento;

    @NotBlank(message = "El número de documento es obligatorio")
    @Size(max = 20, message = "El número de documento no puede exceder 20 caracteres")
    private String numDocumento;

    @Size(max = 20, message = "El NUP no puede exceder 20 caracteres")
    private String nup;

    @Size(max = 20, message = "El NIT no puede exceder 20 caracteres")
    private String nit;

    @NotBlank(message = "La dirección no puede estar vacía")
    @Size(max = 300, message = "La dirección no puede exceder 300 caracteres")
    private String direccion;

    @Size(max = 500, message = "La URL de la foto no puede exceder 500 caracteres")
    private String fotoUrl;

    @NotNull(message = "El género es obligatorio")
    private Integer idGenero;

    @NotNull(message = "El tipo de documento es obligatorio")
    private Integer idTipoDocumento;

    @NotNull(message = "El distrito es obligatorio")
    private Integer idDistrito;
}
