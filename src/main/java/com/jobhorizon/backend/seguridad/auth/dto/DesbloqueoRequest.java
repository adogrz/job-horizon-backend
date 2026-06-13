package com.jobhorizon.backend.seguridad.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Cuerpo de la solicitud para desbloquear una cuenta usando el token recibido por correo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DesbloqueoRequest {

    @Schema(description = "Token de desbloqueo recibido en el correo electrónico", example = "a3f7c2d1-e8b9-4f6a-b1c2-d3e4f5a6b7c8", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El token no puede estar vacío")
    private String token;
}
