package com.jobhorizon.backend.postulante.redsocial;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PostulanteRedSocialId implements Serializable {
    private Integer idUsuario;
    private Integer idTipoRedSocial;
}
