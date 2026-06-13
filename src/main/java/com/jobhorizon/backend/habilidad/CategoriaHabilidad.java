package com.jobhorizon.backend.habilidad;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CategoriaHabilidad")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoriaHabilidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdCategoriaHabilidad")
    private Integer id;

    @Column(name = "Nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "Descripcion", length = 300)
    private String descripcion;
}
