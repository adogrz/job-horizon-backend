package com.jobhorizon.backend.postulante.recomendacion;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RecomendacionId implements Serializable {
    private Integer numRecomendacion;
    private Integer idUsuario;
}
