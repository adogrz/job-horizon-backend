package com.jobhorizon.backend.nivelidioma;

import com.jobhorizon.backend.catalogo.CatalogoEntidad;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "NivelIdioma")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NivelIdioma implements CatalogoEntidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdNivelIdioma")
    private Integer id;

    @Column(name = "Nombre", nullable = false, length = 20)
    private String nombre;

    @Column(name = "OrdenComparacion", nullable = false)
    private Byte ordenComparacion;

    @Column(name = "Activo", nullable = false)
    @Builder.Default
    private Boolean activo = true;
}
