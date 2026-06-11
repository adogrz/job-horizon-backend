package com.jobhorizon.backend.genero;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Genero")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Genero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdGenero")
    private Integer id;

    @Column(name = "Nombre", nullable = false, unique = true, length = 30)
    private String nombre;
}
