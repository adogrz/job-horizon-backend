package com.jobhorizon.backend.departamento;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Departamento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Departamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdDepartamento")
    private Integer id;

    @Column(name = "Nombre", nullable = false, length = 100)
    private String nombre;
}
