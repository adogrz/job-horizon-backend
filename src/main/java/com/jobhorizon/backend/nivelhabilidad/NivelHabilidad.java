package com.jobhorizon.backend.nivelhabilidad;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "NivelHabilidad")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NivelHabilidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdNivelHabilidad")
    private Integer id;

    @Column(name = "Nombre", nullable = false, length = 20)
    private String nombre;

    @Column(name = "OrdenComparacion", nullable = false)
    private Byte ordenComparacion;
}
