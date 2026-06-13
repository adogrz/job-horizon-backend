package com.jobhorizon.backend.postulante.recomendacion.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * DTO de solicitud para crear o actualizar una recomendación.
 */
@Schema(description = "Cuerpo de la solicitud para agregar o actualizar una recomendación laboral")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecomendacionRequest {

    @Schema(description = "Nombre completo de la persona que recomienda (máx. 150 caracteres)", example = "María López Torres", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El nombre del contacto es obligatorio")
    @Size(max = 150, message = "El nombre del contacto no puede exceder 150 caracteres")
    private String nombreContacto;

    @Schema(description = "Teléfono de contacto de quien recomienda (máx. 15 caracteres)", example = "987111222", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El teléfono del contacto es obligatorio")
    @Size(max = 15, message = "El teléfono del contacto no puede exceder 15 caracteres")
    private String telefonoContacto;

    @Schema(description = "ID del tipo de recomendación (obtener de /catalogos/tipos-recomendacion)", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El tipo de recomendación es obligatorio")
    private Integer idTipoRecomendacion;
}
