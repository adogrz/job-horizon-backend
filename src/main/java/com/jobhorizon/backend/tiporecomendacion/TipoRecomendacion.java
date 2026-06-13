package com.jobhorizon.backend.tiporecomendacion;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TipoRecomendacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoRecomendacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdTipoRecomendacion")
    private Integer id;

    @Column(name = "Nombre", nullable = false, length = 30)
    private String nombre;
}
