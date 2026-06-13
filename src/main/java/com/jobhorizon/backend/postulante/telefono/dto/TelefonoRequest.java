package com.jobhorizon.backend.postulante.telefono.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * DTO de solicitud para agregar un teléfono al postulante.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TelefonoRequest {

    @NotBlank(message = "El teléfono no puede estar vacío")
    @Size(max = 15, message = "El teléfono no puede exceder 15 caracteres")
    private String telefono;
}
