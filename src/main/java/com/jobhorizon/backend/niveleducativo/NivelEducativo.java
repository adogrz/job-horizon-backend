package com.jobhorizon.backend.niveleducativo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "NivelEducativo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NivelEducativo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdNivelEducativo")
    private Integer id;

    @Column(name = "Nombre", nullable = false, length = 80)
    private String nombre;

    @Column(name = "OrdenComparacion", nullable = false)
    private Byte ordenComparacion;
}
