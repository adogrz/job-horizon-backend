package com.jobhorizon.backend.postulante.evento.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

/**
 * DTO de solicitud para crear o actualizar un evento.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventoRequest {

    @NotBlank(message = "El nombre del evento es obligatorio")
    @Size(max = 200, message = "El nombre del evento no puede exceder 200 caracteres")
    private String nombreEvento;

    @NotBlank(message = "El lugar es obligatorio")
    @Size(max = 200, message = "El lugar no puede exceder 200 caracteres")
    private String lugar;

    @NotBlank(message = "El anfitrión es obligatorio")
    @Size(max = 200, message = "El anfitrión no puede exceder 200 caracteres")
    private String anfitrion;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @NotNull(message = "El tipo de participación es obligatorio")
    private Integer idTipoParticipacion;

    @NotNull(message = "El país es obligatorio")
    private Integer idPais;
}
