package com.jobhorizon.backend.seguridad.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DesbloqueoRequest {
    @NotBlank(message = "El token no puede estar vacío")
    private String token;
}
