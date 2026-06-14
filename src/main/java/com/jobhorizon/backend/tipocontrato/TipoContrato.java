package com.jobhorizon.backend.tipocontrato;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TipoContrato")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoContrato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdTipoContrato")
    private Integer id;

    @Column(name = "Nombre", nullable = false, length = 80)
    private String nombre;
}
