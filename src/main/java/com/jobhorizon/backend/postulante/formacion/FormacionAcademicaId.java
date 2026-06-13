package com.jobhorizon.backend.postulante.formacion;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FormacionAcademicaId implements Serializable {
    private Integer numFormacion;
    private Integer idUsuario;
}
