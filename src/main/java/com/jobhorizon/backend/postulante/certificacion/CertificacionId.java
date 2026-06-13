package com.jobhorizon.backend.postulante.certificacion;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CertificacionId implements Serializable {
    private Integer codCert;
    private Integer idUsuario;
}
