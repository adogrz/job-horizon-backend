package com.jobhorizon.backend.postulante.habilidad;

import lombok.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PostulanteHabilidadId implements Serializable {
    private Integer idUsuario;
    private Integer idHabilidad;
}
