package com.jobhorizon.backend.postulante.evento.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

/**
 * DTO de solicitud para crear o actualizar un evento.
 */
@Schema(description = "Cuerpo de la solicitud para agregar o actualizar un evento en el que participó el postulante")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventoRequest {

    @Schema(description = "Nombre del evento (máx. 200 caracteres)", example = "Google DevFest Lima 2023", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El nombre del evento es obligatorio")
    @Size(max = 200, message = "El nombre del evento no puede exceder 200 caracteres")
    private String nombreEvento;

    @Schema(description = "Lugar donde se realizó el evento (máx. 200 caracteres)", example = "Centro de Convenciones Lima", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El lugar es obligatorio")
    @Size(max = 200, message = "El lugar no puede exceder 200 caracteres")
    private String lugar;

    @Schema(description = "Organización o empresa anfitriona del evento (máx. 200 caracteres)", example = "Google Developers Group Lima", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El anfitrión es obligatorio")
    @Size(max = 200, message = "El anfitrión no puede exceder 200 caracteres")
    private String anfitrion;

    @Schema(description = "Fecha del evento en formato YYYY-MM-DD", example = "2023-11-18", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @Schema(description = "ID del tipo de participación (obtener de /catalogos/tipos-participacion)", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El tipo de participación es obligatorio")
    private Integer idTipoParticipacion;

    @Schema(description = "ID del país donde se realizó el evento (obtener de /catalogos/paises)", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El país es obligatorio")
    private Integer idPais;
}
