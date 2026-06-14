package com.jobhorizon.backend.ofertatrabajo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "EstadoAplicacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadoAplicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdEstadoAplicacion")
    private Integer id;

    @Column(name = "Nombre", nullable = false, length = 30)
    private String nombre;
}
