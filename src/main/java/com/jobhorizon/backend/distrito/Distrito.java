package com.jobhorizon.backend.distrito;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Distrito")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Distrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdDistrito")
    private Integer id;

    @Column(name = "Nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "IdDepartamento", nullable = false)
    private Integer idDepartamento;
}
