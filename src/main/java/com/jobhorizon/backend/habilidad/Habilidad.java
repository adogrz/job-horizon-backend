package com.jobhorizon.backend.habilidad;

import com.jobhorizon.backend.catalogo.CatalogoEntidad;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Habilidad")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Habilidad implements CatalogoEntidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdHabilidad")
    private Integer id;

    @Column(name = "Nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "Descripcion", length = 300)
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdCategoriaHabilidad", nullable = false)
    private CategoriaHabilidad categoriaHabilidad;

    @Column(name = "Activo", nullable = false)
    @Builder.Default
    private Boolean activo = true;
}
