package com.jobhorizon.backend.pais;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Pais")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pais {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdPais")
    private Integer id;

    @Column(name = "Nombre", nullable = false, length = 100)
    private String nombre;
}
