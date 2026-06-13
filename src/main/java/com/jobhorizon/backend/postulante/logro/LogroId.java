package com.jobhorizon.backend.postulante.logro;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class LogroId implements Serializable {
    private Integer numLogro;
    private Integer idUsuario;
}
