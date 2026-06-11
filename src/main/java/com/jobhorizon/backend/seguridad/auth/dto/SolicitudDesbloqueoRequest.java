package com.jobhorizon.backend.seguridad.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudDesbloqueoRequest {
    @NotBlank(message = "El correo no puede estar vacío")
    @Email(message = "El correo debe ser una dirección de correo válida")
    private String correo;
}
