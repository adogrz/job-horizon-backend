package com.jobhorizon.backend.seguridad.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Cuerpo de la solicitud para solicitar el desbloqueo de una cuenta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudDesbloqueoRequest {

    @Schema(description = "Correo electrónico de la cuenta a desbloquear", example = "juan.perez@correo.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El correo no puede estar vacío")
    @Email(message = "El correo debe ser una dirección de correo válida")
    private String correo;
}
