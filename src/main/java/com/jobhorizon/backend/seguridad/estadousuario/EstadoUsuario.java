package com.jobhorizon.backend.seguridad.estadousuario;

import com.jobhorizon.backend.catalogo.CatalogoEntidad;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "EstadoUsuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadoUsuario implements CatalogoEntidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdEstadoUsuario")
    private Integer id;

    @Column(name = "Nombre", nullable = false, unique = true, length = 30)
    private String nombre;

    @Column(name = "Activo", nullable = false)
    @Builder.Default
    private Boolean activo = true;
}
