package com.jobhorizon.backend.habilidad;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Habilidad")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Habilidad {
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
}
