package com.jobhorizon.backend.postulante.certificacion.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

/**
 * DTO de solicitud para crear o actualizar una certificación.
 */
@Schema(description = "Cuerpo de la solicitud para agregar o actualizar una certificación")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertificacionRequest {

    @Schema(description = "Código o identificador único de la certificación emitido por la institución (opcional, máx. 100 caracteres)", example = "AWS-SAA-C03")
    @Size(max = 100, message = "El código de certificación no puede exceder 100 caracteres")
    private String codigoCertificacion;

    @Schema(description = "Nombre de la certificación (máx. 200 caracteres)", example = "AWS Certified Solutions Architect – Associate", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 200, message = "El nombre no puede exceder 200 caracteres")
    private String nombre;

    @Schema(description = "ID del tipo de certificación (obtener de /catalogos/tipos-certificacion)", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El tipo de certificación es obligatorio")
    private Integer idTipoCertificacion;

    @Schema(description = "Nombre de la institución que emitió la certificación (máx. 200 caracteres)", example = "Amazon Web Services", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "La institución es obligatoria")
    @Size(max = 200, message = "La institución no puede exceder 200 caracteres")
    private String institucion;

    @Schema(description = "Fecha de obtención de la certificación en formato YYYY-MM-DD", example = "2023-08-20", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "La fecha de obtención es obligatoria")
    private LocalDate fechaObtencion;

    @Schema(description = "URL del archivo o imagen del certificado (opcional, máx. 500 caracteres)", example = "https://storage.jobhorizon.com/certificados/aws-saa.pdf")
    @Size(max = 500, message = "La URL del archivo no puede exceder 500 caracteres")
    private String archivoUrl;
}
