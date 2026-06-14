package com.jobhorizon.backend.empresa.telefono.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Schema(description = "Solicitud para registrar o eliminar un teléfono de la empresa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpresaTelefonoRequest {

    @Schema(description = "Número de teléfono en formato ####-#### o +########### / +############", example = "2222-2222", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El teléfono no puede estar vacío")
    @Size(max = 15, message = "El teléfono no puede exceder 15 caracteres")
    @Pattern(regexp = "^(\\d{4}-\\d{4}|\\+\\d{11,12})$", message = "El teléfono debe cumplir con el formato ####-#### o +########### / +############")
    private String telefono;
}
