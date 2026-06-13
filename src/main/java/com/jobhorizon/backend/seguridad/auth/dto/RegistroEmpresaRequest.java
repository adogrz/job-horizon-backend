package com.jobhorizon.backend.seguridad.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Schema(description = "Cuerpo de la solicitud para registrar una nueva empresa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroEmpresaRequest {

    @Schema(description = "Correo electrónico de la empresa (debe ser único)", example = "contacto@miempresa.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El correo no puede estar vacío")
    @Email(message = "El correo debe ser una dirección de correo válida")
    private String correo;

    @Schema(description = "Contraseña para la cuenta de la empresa", example = "EmpresaPass456!", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password;

    @Schema(description = "Nombre comercial de la empresa", example = "Tech Solutions SAC", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El nombre comercial es obligatorio")
    private String nombreComercial;

    @Schema(description = "Razón social de la empresa", example = "Tech Solutions Sociedad Anónima Cerrada", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "La razón social es obligatoria")
    private String razonSocial;

    @Schema(description = "Número de Identificación Tributaria de la empresa", example = "20123456789", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El NIT es obligatorio")
    private String nit;

    @Schema(description = "URL del sitio web de la empresa (opcional)", example = "https://www.miempresa.com")
    private String sitioWeb;

    @Schema(description = "Descripción o giro de la empresa (opcional)", example = "Empresa de desarrollo de software y consultoría tecnológica")
    private String descripcion;

    @Schema(description = "ID del distrito donde se ubica la empresa (obtener de /catalogos/departamentos/{id}/distritos)", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El distrito es obligatorio")
    private Integer idDistrito;
}
