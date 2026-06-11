package com.jobhorizon.backend.seguridad.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroEmpresaRequest {
    @NotBlank(message = "El correo no puede estar vacío")
    @Email(message = "El correo debe ser una dirección de correo válida")
    private String correo;

    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password;

    @NotBlank(message = "El nombre comercial es obligatorio")
    private String nombreComercial;

    @NotBlank(message = "La razón social es obligatoria")
    private String razonSocial;

    @NotBlank(message = "El NIT es obligatorio")
    private String nit;

    private String sitioWeb;

    private String descripcion;

    @NotNull(message = "El distrito es obligatorio")
    private Integer idDistrito;
}
