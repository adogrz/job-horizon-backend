package com.jobhorizon.backend.modalidad;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Modalidad")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Modalidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdModalidad")
    private Integer id;

    @Column(name = "Nombre", nullable = false, length = 30)
    private String nombre;
}
