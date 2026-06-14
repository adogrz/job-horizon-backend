package com.jobhorizon.backend.empresa.telefono;

import com.jobhorizon.backend.empresa.Empresa;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "EmpresaTelefono")
@IdClass(EmpresaTelefonoId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpresaTelefono {
    @Id
    @Column(name = "IdUsuario")
    private Integer idUsuario;

    @Id
    @Column(name = "Telefono", length = 15)
    private String telefono;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdUsuario", insertable = false, updatable = false)
    private Empresa empresa;
}
