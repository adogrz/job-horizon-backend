package com.jobhorizon.backend.departamento;

import com.jobhorizon.backend.catalogo.CatalogoEntidad;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Departamento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Departamento implements CatalogoEntidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdDepartamento")
    private Integer id;

    @Column(name = "Nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "Activo", nullable = false)
    @Builder.Default
    private Boolean activo = true;
}
