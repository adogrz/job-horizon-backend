package com.jobhorizon.backend.modalidad;

import com.jobhorizon.backend.catalogo.CatalogoEntidad;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Modalidad")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Modalidad implements CatalogoEntidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdModalidad")
    private Integer id;

    @Column(name = "Nombre", nullable = false, length = 30)
    private String nombre;

    @Column(name = "Activo", nullable = false)
    @Builder.Default
    private Boolean activo = true;
}
