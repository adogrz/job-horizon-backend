package com.jobhorizon.backend.postulante.evento;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EventoId implements Serializable {
    private Integer numEvento;
    private Integer idUsuario;
}
