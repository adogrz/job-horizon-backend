package com.jobhorizon.backend.seguridad.administrador;

import com.jobhorizon.backend.seguridad.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Administrador")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Administrador {
    @Id
    @Column(name = "IdUsuario")
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "IdUsuario")
    private Usuario usuario;
}
