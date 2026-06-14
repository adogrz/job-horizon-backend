package com.jobhorizon.backend.estadooferta;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "EstadoOferta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadoOferta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdEstadoOferta")
    private Integer id;

    @Column(name = "Nombre", nullable = false, length = 30)
    private String nombre;
}
