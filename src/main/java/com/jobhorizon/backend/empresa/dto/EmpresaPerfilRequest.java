package com.jobhorizon.backend.empresa.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Schema(description = "Solicitud para registrar o actualizar el perfil de la empresa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpresaPerfilRequest {

    @Schema(description = "Nombre comercial de la empresa (máx. 150 caracteres)", example = "Mi Empresa S.A.", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El nombre comercial no puede estar vacío")
    @Size(max = 150, message = "El nombre comercial no puede exceder 150 caracteres")
    private String nombreComercial;

    @Schema(description = "Razón social de la empresa (máx. 200 caracteres)", example = "Compañía de Servicios y Comercio S.A. de C.V.", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "La razón social no puede estar vacía")
    @Size(max = 200, message = "La razón social no puede exceder 200 caracteres")
    private String razonSocial;

    @Schema(description = "Número de Identificación Tributaria (NIT) en formato ####-######-###-#", example = "0614-150695-102-1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El NIT no puede estar vacío")
    @Size(max = 17, message = "El NIT no puede exceder 17 caracteres")
    @Pattern(regexp = "^\\d{4}-\\d{6}-\\d{3}-\\d$", message = "El NIT debe cumplir con el formato ####-######-###-#")
    private String nit;

    @Schema(description = "Sitio web de la empresa (opcional, máx. 300 caracteres)", example = "https://www.miempresa.com")
    @Size(max = 300, message = "El sitio web no puede exceder 300 caracteres")
    private String sitioWeb;

    @Schema(description = "Descripción de la empresa (opcional)", example = "Empresa líder en tecnología...")
    private String descripcion;

    @Schema(description = "URL del logo de la empresa (opcional, máx. 500 caracteres)", example = "https://storage.jobhorizon.com/logos/miempresa.png")
    @Size(max = 500, message = "La URL del logo no puede exceder 500 caracteres")
    private String logoUrl;

    @Schema(description = "ID del distrito donde se ubica la empresa", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El distrito es obligatorio")
    private Integer idDistrito;
}
