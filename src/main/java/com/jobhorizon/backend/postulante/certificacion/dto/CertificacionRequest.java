package com.jobhorizon.backend.postulante.certificacion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

/**
 * DTO de solicitud para crear o actualizar una certificación.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertificacionRequest {

    @Size(max = 100, message = "El código de certificación no puede exceder 100 caracteres")
    private String codigoCertificacion;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 200, message = "El nombre no puede exceder 200 caracteres")
    private String nombre;

    @NotNull(message = "El tipo de certificación es obligatorio")
    private Integer idTipoCertificacion;

    @NotBlank(message = "La institución es obligatoria")
    @Size(max = 200, message = "La institución no puede exceder 200 caracteres")
    private String institucion;

    @NotNull(message = "La fecha de obtención es obligatoria")
    private LocalDate fechaObtencion;

    @Size(max = 500, message = "La URL del archivo no puede exceder 500 caracteres")
    private String archivoUrl;
}
