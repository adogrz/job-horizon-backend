package com.jobhorizon.backend.postulante.examen;

import lombok.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ExamenId implements Serializable {
    private Integer numExamen;
    private Integer idUsuario;
}
