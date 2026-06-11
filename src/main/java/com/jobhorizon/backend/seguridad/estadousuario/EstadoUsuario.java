package com.jobhorizon.backend.seguridad.estadousuario;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "EstadoUsuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadoUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdEstadoUsuario")
    private Integer id;

    @Column(name = "Nombre", nullable = false, unique = true, length = 30)
    private String nombre;
}
