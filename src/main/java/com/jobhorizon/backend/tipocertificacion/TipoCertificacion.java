package com.jobhorizon.backend.tipocertificacion;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TipoCertificacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoCertificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdTipoCertificacion")
    private Integer id;

    @Column(name = "Nombre", nullable = false, length = 80)
    private String nombre;
}
