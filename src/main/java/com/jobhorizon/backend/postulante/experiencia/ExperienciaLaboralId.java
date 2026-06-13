package com.jobhorizon.backend.postulante.experiencia;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ExperienciaLaboralId implements Serializable {
    private Integer numExp;
    private Integer idUsuario;
}
