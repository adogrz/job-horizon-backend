package com.jobhorizon.backend.empresa.telefono;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EmpresaTelefonoId implements Serializable {
    private Integer idUsuario;
    private String telefono;
}
