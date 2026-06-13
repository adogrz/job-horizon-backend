package com.jobhorizon.backend.idioma;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Idioma")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Idioma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdIdioma")
    private Integer id;

    @Column(name = "Nombre", nullable = false, length = 80)
    private String nombre;
}
