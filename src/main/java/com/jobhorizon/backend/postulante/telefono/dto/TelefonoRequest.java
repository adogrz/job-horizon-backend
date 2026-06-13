package com.jobhorizon.backend.postulante.telefono.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * DTO de solicitud para agregar un teléfono al postulante.
 */
@Schema(description = "Cuerpo de la solicitud para agregar un número de teléfono al perfil")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TelefonoRequest {

    @Schema(description = "Número de teléfono (máx. 15 caracteres, puede incluir código de país)", example = "987654321", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El teléfono no puede estar vacío")
    @Size(max = 15, message = "El teléfono no puede exceder 15 caracteres")
    private String telefono;
}
