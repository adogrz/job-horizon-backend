package com.jobhorizon.backend.postulante.publicacion;

import lombok.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PublicacionId implements Serializable {
    private Integer numPublicacion;
    private Integer idUsuario;
}
