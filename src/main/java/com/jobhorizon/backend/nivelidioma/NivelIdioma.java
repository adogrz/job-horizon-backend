package com.jobhorizon.backend.nivelidioma;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "NivelIdioma")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NivelIdioma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdNivelIdioma")
    private Integer id;

    @Column(name = "Nombre", nullable = false, length = 20)
    private String nombre;

    @Column(name = "OrdenComparacion", nullable = false)
    private Byte ordenComparacion;
}
