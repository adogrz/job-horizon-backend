package com.jobhorizon.backend.seguridad.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroPostulanteRequest {
    @NotBlank(message = "El correo no puede estar vacío")
    @Email(message = "El correo debe ser una dirección de correo válida")
    private String correo;

    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password;

    @NotBlank(message = "Los nombres no pueden estar vacíos")
    private String nombres;

    @NotBlank(message = "Los apellidos no pueden estar vacíos")
    private String apellidos;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate fechaNacimiento;

    @NotBlank(message = "El número de documento es obligatorio")
    private String numDocumento;

    private String nup;

    private String nit;

    @NotBlank(message = "La dirección no puede estar vacía")
    private String direccion;

    @NotNull(message = "El género es obligatorio")
    private Integer idGenero;

    @NotNull(message = "El tipo de documento es obligatorio")
    private Integer idTipoDocumento;

    @NotNull(message = "El distrito es obligatorio")
    private Integer idDistrito;
}
