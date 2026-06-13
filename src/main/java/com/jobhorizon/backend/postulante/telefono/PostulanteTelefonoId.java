package com.jobhorizon.backend.postulante.telefono;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PostulanteTelefonoId implements Serializable {
    private Integer idUsuario;
    private String telefono;
}
