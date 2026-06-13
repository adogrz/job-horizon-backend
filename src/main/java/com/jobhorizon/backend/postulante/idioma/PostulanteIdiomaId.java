package com.jobhorizon.backend.postulante.idioma;

import lombok.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PostulanteIdiomaId implements Serializable {
    private Integer idUsuario;
    private Integer idIdioma;
}
